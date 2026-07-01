package com.rcdriver.dr.utils;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

import java.util.concurrent.TimeUnit;

import com.rcdriver.dr.R;
import com.rcdriver.dr.activity.MainActivity;
import com.rcdriver.dr.constants.BaseApp;
import com.rcdriver.dr.json.StatusRequest;
import com.rcdriver.dr.json.StatusResponse;
import com.rcdriver.dr.json.UpdateLocationRequestJson;
import com.rcdriver.dr.json.UpdateLocationResponseJson;
import com.rcdriver.dr.models.User;
import com.rcdriver.dr.utils.api.ServiceGenerator;
import com.rcdriver.dr.utils.api.service.DriverService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FloatingViewService extends Service {
    private static final String TAG = FloatingViewService.class.getSimpleName();
    public static final int GPS_NOTIFICATION = 1;

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;

    private WindowManager mWindowManager;
    private View mFloatingView;
    private WindowManager.LayoutParams params;
    private SettingPreference sp;
    private Handler handler = new Handler();
    private Runnable runnable;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sp = new SettingPreference(this);
        mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_widget, null);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        createLocationRequest();
        createLocationCallback();

        setupFloatingView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupFloatingView() {
        int windowManagerType;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            windowManagerType = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            windowManagerType = WindowManager.LayoutParams.TYPE_PHONE;
        }

        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                windowManagerType,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.LEFT;
        params.x = 0;
        params.y = 0;
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        if (mWindowManager != null) {
            mWindowManager.addView(mFloatingView, params);
        }

        ImageView openButton = mFloatingView.findViewById(R.id.collapsed_iv);
        openButton.setOnTouchListener(new View.OnTouchListener() {
            private int initialX, initialY;
            private float initialTouchX, initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        int xDiff = (int) (event.getRawX() - initialTouchX);
                        int yDiff = (int) (event.getRawY() - initialTouchY);
                        if (xDiff < 10 && yDiff < 10) {
                            Intent intent = new Intent(FloatingViewService.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            stopSelf();
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        mWindowManager.updateViewLayout(mFloatingView, params);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(TAG, "Starting the service...");
        startForeground(GPS_NOTIFICATION, createNotification());
        startLocationUpdates();
        return START_REDELIVER_INTENT;
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
                    Log.d(TAG, "Location Changed: " + location.getLatitude() + ", " + location.getLongitude());
                    updateLocationToServer(location);
                }
            }
        };
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Location updates started successfully."))
                .addOnFailureListener(e -> Log.e(TAG, "Failed to start location updates.", e));
    }

    private Notification createNotification() {
        String NOTIFICATION_CHANNEL_ID = "com.asia.pengemudi.location";
        String channelName = "Layanan Latar Belakang";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(chan);
        }

        RefreshHome(sp.getSetting()[12]);
        String status;
        switch (sp.getSetting()[12]) {
            case "2":
                status = "Memproses Pesanan";
                break;
            case "3":
                status = "Menyelesaikan Pesanan";
                break;
            case "4":
                status = "Istirahat";
                break;
            case "1":
            default:
                status = "Menunggu Pesanan";
                break;
        }

        return new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setOngoing(true)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(status)
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "Stopping the service...");

        fusedLocationClient.removeLocationUpdates(locationCallback);

        if (mFloatingView != null && mWindowManager != null) {
            mWindowManager.removeView(mFloatingView);
        }

        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
        stopForeground(true);
    }

    private void updateLocationToServer(Location location) {
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        if (loginUser != null) {
            // Cek apakah objek masih valid sebelum digunakan
            if (!loginUser.isValid()) return;

            DriverService service = ServiceGenerator.createService(DriverService.class, loginUser.getEmail(), loginUser.getPassword());
            UpdateLocationRequestJson request = new UpdateLocationRequestJson();
            request.setId(loginUser.getId());
            request.setLatitude(String.valueOf(location.getLatitude()));
            request.setLongitude(String.valueOf(location.getLongitude()));
            request.setBearing(String.valueOf(location.getBearing()));
            request.setStatus(sp.getSetting()[12]);
            service.updatelocation(request).enqueue(new Callback<UpdateLocationResponseJson>() {
                @Override
                public void onResponse(@NonNull Call<UpdateLocationResponseJson> call, @NonNull Response<UpdateLocationResponseJson> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        if (response.body().mesage.equals("success")) {
                            Log.d("FloatUpdate", "Success: " + response.body().mesage + ", Status: " + sp.getSetting()[12]);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UpdateLocationResponseJson> call, @NonNull Throwable t) {
                    Log.e("FloatUpdate", "Failure: " + t.getMessage());
                }
            });
        }
    }

    private void RefreshHome(String status) {
        // Inisialisasi service bisa sekali saja
        DriverService service = ServiceGenerator.createService(DriverService.class, "dummy", "dummy");
        handler = new Handler(Looper.getMainLooper());
        this.runnable = () -> {
            // Ambil objek User terbaru TEPAT sebelum digunakan
            final User loginUser = BaseApp.getInstance(getApplicationContext()).getLoginUser();

            // Cek apakah user masih ada dan valid
            if (loginUser != null && loginUser.isValid() && "1".equals(status)) {
                StatusRequest request = new StatusRequest();
                request.setId_driver(loginUser.getId()); // Panggilan ini sekarang aman
                request.setStatus("1");

                // Buat ulang service dengan kredensial yang valid
                DriverService validService = ServiceGenerator.createService(DriverService.class, loginUser.getEmail(), loginUser.getPassword());
                validService.reloadstatus(request).enqueue(new Callback<StatusResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<StatusResponse> call, @NonNull Response<StatusResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Log.d("ReloadStatus", response.body().mesage);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<StatusResponse> call, @NonNull Throwable t) {
                        Log.e("ReloadStatus", "Failure: " + t.getMessage());
                    }
                });
                handler.postDelayed(this.runnable, 3000);
            } else {
                Log.d("ReloadStatus", "Polling stopped, user is null or status is not '1'.");
                handler.removeCallbacks(this.runnable);
            }
        };
        handler.postDelayed(runnable, 5000);
    }
}