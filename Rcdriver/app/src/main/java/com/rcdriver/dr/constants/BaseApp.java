package com.rcdriver.dr.constants;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.multidex.MultiDex;

import com.rcdriver.dr.models.MyRealmModule;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;

import com.rcdriver.dr.json.UpdateLocationRequestJson;
import com.rcdriver.dr.json.UpdateLocationResponseJson;
import com.rcdriver.dr.models.FirebaseToken;
import com.rcdriver.dr.models.User;
import com.rcdriver.dr.utils.MyLocationService;
import com.rcdriver.dr.utils.api.ServiceGenerator;
import com.rcdriver.dr.utils.api.service.DriverService;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseApp extends Application {

    private static final int SCHEMA_VERSION = 1;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private User loginUser;
    private Realm realmInstance;

    public static BaseApp getInstance(Context context) {
        return (BaseApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // WAJIB init Realm sebelum buat konfigurasi
        Realm.init(this);

        // Konfigurasi Realm
// Konfigurasi Realm
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("pengemudi.realm")
                .schemaVersion(SCHEMA_VERSION)
                .deleteRealmIfMigrationNeeded()
                .modules(new MyRealmModule())
                .allowWritesOnUiThread(true) // <-- TAMBAHKAN BARIS INI
                .build();
        Realm.setDefaultConfiguration(config);

        realmInstance = Realm.getDefaultInstance();

        // Ambil token Firebase
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String fcmToken = task.getResult();
                        saveFirebaseToken(fcmToken);
                    }
                });

        // Subscribe ke topic
        FirebaseMessaging.getInstance().subscribeToTopic("ouride");
        FirebaseMessaging.getInstance().subscribeToTopic("driver");

        // Mulai proses lain
        updatelocation();
        start();
    }

    private void saveFirebaseToken(String fcmToken) {
        if (fcmToken == null) return;
        FirebaseToken token = new FirebaseToken(fcmToken);

        // Gunakan executeTransactionAsync untuk berjalan di background thread
        realmInstance.executeTransactionAsync(realm -> {
            realm.delete(FirebaseToken.class);
            realm.insertOrUpdate(token);
        }, () -> {
            // Transaksi berhasil (berjalan di UI Thread)
            android.util.Log.d("Realm", "Firebase Token saved successfully!");
        }, error -> {
            // Transaksi gagal (berjalan di UI Thread)
            android.util.Log.e("Realm", "Failed to save Firebase Token", error);
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public User getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }

    public final Realm getRealmInstance() {
        return realmInstance;
    }

    private void start() {
        Realm realm = getRealmInstance();
        User user = realm.where(User.class).findFirst();
        if (user != null) {
            setLoginUser(user);
        }
    }

    private void updatelocation() {
        buildlocation();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, getPendingIntent());
    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(this, MyLocationService.class);
        intent.setAction(MyLocationService.ACTION_PROCESS_UPDATE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_MUTABLE);
        } else {
            return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
    }

    private void buildlocation() {
        locationRequest = new LocationRequest.Builder(
                LocationRequest.PRIORITY_HIGH_ACCURACY, 5000L
        )
                .setMinUpdateIntervalMillis(3000L)
                .setMinUpdateDistanceMeters(10f)
                .build();
    }

    public void Updatelocationdata(final Location location) {
        new Handler(Looper.getMainLooper()).post(() -> onLocationChanged(location));
    }

    public void onLocationChanged(Location location) {
        if (location != null && getLoginUser() != null) {
            User loginUser = getLoginUser();
            DriverService service = ServiceGenerator.createService(
                    DriverService.class,
                    loginUser.getEmail(),
                    loginUser.getPassword()
            );

            UpdateLocationRequestJson request = new UpdateLocationRequestJson();
            request.setId(loginUser.getId());
            request.setLatitude(String.valueOf(location.getLatitude()));
            request.setLongitude(String.valueOf(location.getLongitude()));
            request.setBearing(String.valueOf(location.getBearing()));

            service.updatelocation(request).enqueue(new Callback<UpdateLocationResponseJson>() {
                @Override
                public void onResponse(@NonNull Call<UpdateLocationResponseJson> call,
                                       @NonNull Response<UpdateLocationResponseJson> response) {
                    if (response.isSuccessful()) {
                        if (Objects.requireNonNull(response.body()).mesage.equals("success")) {
                            android.util.Log.e("updatelokasi", response.body().mesage);
                        }
                    }
                }

                @SuppressLint("SetTextI18n")
                @Override
                public void onFailure(@NonNull Call<UpdateLocationResponseJson> call,
                                      @NonNull Throwable t) {
                    t.printStackTrace();
                    android.util.Log.e("updatelokasi", t.getMessage());
                }
            });
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (realmInstance != null && !realmInstance.isClosed()) {
            realmInstance.close();
        }
    }
}
