package com.rcdriver.dr.utils;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

import com.rcdriver.dr.R;
import com.rcdriver.dr.constants.BaseApp;
import com.rcdriver.dr.json.UpdateLocationRequestJson;
import com.rcdriver.dr.json.UpdateLocationResponseJson;
import com.rcdriver.dr.models.User;
import com.rcdriver.dr.utils.api.ServiceGenerator;
import com.rcdriver.dr.utils.api.service.DriverService;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// DIUBAH: Menggunakan "extends Service", bukan "BroadcastReceiver"
public class MyLocationService extends Service {

    public static final String ACTION_PROCESS_UPDATE = "1";
    private static final String TAG = "MyLocationService";
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        createLocationRequest();
        createLocationCallback();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service started");
        startForeground(1, createNotification("Driver Online")); // ID notifikasi harus > 0
        startLocationUpdates();
        return START_STICKY; // Service akan coba dijalankan ulang jika dihentikan sistem
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Location updates started successfully."))
                .addOnFailureListener(e -> Log.e(TAG, "Failed to start location updates.", e));
    }

    private void createLocationRequest() {
        locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, TimeUnit.SECONDS.toMillis(15))
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(TimeUnit.SECONDS.toMillis(10))
                .setMaxUpdateDelayMillis(TimeUnit.SECONDS.toMillis(30))
                .build();
    }

    private void createLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    Log.d(TAG, "New Location: " + location.getLatitude() + ", " + location.getLongitude());
                    updateLocationToServer(location);
                }
            }
        };
    }

    private Notification createNotification(String text) {
        String NOTIFICATION_CHANNEL_ID = "com.asia.pengemudi.location_service_channel";
        String channelName = "Layanan Lokasi";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_LOW);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            notificationManager.createNotificationChannel(chan);
        }

        return new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setOngoing(true)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Aplikasi Pengemudi")
                .setContentText(text)
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
    }

    private void updateLocationToServer(Location location) {
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        // Cek jika user masih login
        if (loginUser == null) {
            stopSelf(); // Hentikan service jika pengguna sudah logout
            return;
        }

        DriverService service = ServiceGenerator.createService(DriverService.class, loginUser.getEmail(), loginUser.getPassword());
        UpdateLocationRequestJson request = new UpdateLocationRequestJson();
        request.setId(loginUser.getId());
        request.setLatitude(String.valueOf(location.getLatitude()));
        request.setLongitude(String.valueOf(location.getLongitude()));
        request.setBearing(String.valueOf(location.getBearing()));

        // Anda bisa mendapatkan status driver dari SettingPreference atau sumber lain
         request.setStatus("1");

        service.updatelocation(request).enqueue(new Callback<UpdateLocationResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<UpdateLocationResponseJson> call, @NonNull Response<UpdateLocationResponseJson> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Location updated to server: " + response.body().mesage);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UpdateLocationResponseJson> call, @NonNull Throwable t) {
                Log.e(TAG, "Failed to update location to server: " + t.getMessage());
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Service destroyed");
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}