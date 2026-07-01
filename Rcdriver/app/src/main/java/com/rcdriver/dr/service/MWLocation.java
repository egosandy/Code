package com.rcdriver.dr.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;

import com.rcdriver.dr.R;
import com.rcdriver.dr.constants.Constants;

@SuppressLint("NewApi")
public class MWLocation extends Service {
    public static final String NOTIFICATION_CHANNEL_ID = "4655";
    private static final String TAG = "LocationService";
    private static final int LOCATION_INTERVAL = 1000;
    private Context mContext;
    private LocationRequest locationRequest;
    private static final long INTERVAL = 2000; //1.5 min
    private static final long FASTEST_INTERVAL = 1000; //1.5 min
    private static final long DISPLACEMENT = 5; //5 meter
    private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderApi fusedLocationProviderApi;
    private static final int REQUEST_LOCATION = 0;
    private Location mLastLocation;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        mContext = this;
        Constants.isBackground = true;
       // getLocation();
    }
    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void Posnotif(String Pesan){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String id = "_channel_01";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel = new NotificationChannel(id, "notification", importance);
            mChannel.enableLights(true);
            Notification notification = new Notification.Builder(getApplicationContext(), id)
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(Pesan)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .build();

            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(mChannel);
                mNotificationManager.notify(2, notification);
            }

            startForeground(2, notification);
        }
    }
    //-----------------------------------------------------------------------------------------------------
}