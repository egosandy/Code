package com.rcdriver.cs.activity;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import java.util.Objects;

import com.rcdriver.cs.R;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.fragment.EnableLlocationFragment;
import com.rcdriver.cs.json.SettingResponse;
import com.rcdriver.cs.models.SettingModel;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.utils.Log;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    List<SettingModel> SettingList;
    private String cekotp = "1";
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        title = findViewById(R.id.title);
        removeNotif();
        sharedPreferences = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
//        GetSetting();

    }
    private void setUpFadeAnimation(final TextView textView) {
        // Start from 0.1f if you desire 90% fade animation
        final Animation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(1000);
        fadeIn.setStartOffset(3000);
        // End to 0.1f if you desire 90% fade animation
        final Animation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(1000);
        fadeOut.setStartOffset(3000);

        fadeIn.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationEnd(Animation arg0) {
                // start fadeOut when fadeIn ends (continue)
                textView.startAnimation(fadeOut);
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationStart(Animation arg0) {
            }
        });

        fadeOut.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationEnd(Animation arg0) {
                // start fadeIn when fadeOut ends (repeat)
                textView.startAnimation(fadeIn);
                final User user = BaseApp.getInstance(SplashActivity.this).getLoginUser();
                if (user != null) {
                    if (ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        enable_location();
                    } else {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    if (ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        enable_location();
                    } else {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationStart(Animation arg0) {
            }
        });

        textView.startAnimation(fadeOut);
    }
    @Override
    protected void onResume(){
        super.onResume();
        setUpFadeAnimation(title);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }
    public void GPSStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean GpsStatus = Objects.requireNonNull(locationManager).isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!GpsStatus) {
            Toast.makeText(this, "On Location in High Accuracy", Toast.LENGTH_SHORT).show();
            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 2);

        } else {


            GetCurrentlocation();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            GPSStatus();
        }
    }


    private void GetCurrentlocation() {
        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            enable_location();
            return;
        }

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {

                            // if we successfully get the location of the user then we will save the locatio into
                            //locally and go to the Main view
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(String.valueOf(Constants.LATITUDE), "" + location.getLatitude());
                            editor.putString(String.valueOf(Constants.LONGITUDE), "" + location.getLongitude());
                            editor.apply();
                            Constants.LATITUDE = location.getLatitude();
                            Constants.LONGITUDE = location.getLongitude();
                            GetSetting();
                        } else {

                            if (sharedPreferences.getString(String.valueOf(Constants.LATITUDE), "").equals("") || sharedPreferences.getString(String.valueOf(Constants.LONGITUDE), "").equals("")) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(String.valueOf(Constants.LATITUDE), "33.738045");
                                editor.putString(String.valueOf(Constants.LONGITUDE), "73.084488");
                                editor.apply();
                                Constants.LATITUDE = Double.valueOf("33.738045");
                                Constants.LONGITUDE = Double.valueOf("73.084488");
                            }
                            GetSetting();
                        }
                    }
                });
    }


    private void enable_location() {
        EnableLlocationFragment enable_llocationFragment = new EnableLlocationFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.from_right, R.anim.to_left, R.anim.from_left, R.anim.to_right);
        getSupportFragmentManager().popBackStackImmediate();
        transaction.replace(R.id.splash, enable_llocationFragment).addToBackStack(null).commit();

    }

    private void removeNotif() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Objects.requireNonNull(notificationManager).cancel(0);
    }
    //--------------------------- get Setting ---------------------------------------
    private void GetSetting() {
        try {
            final User user = BaseApp.getInstance(this).getLoginUser();
            UserService service = ServiceGenerator.createService(UserService.class, "admin", "12345");
            service.setting().enqueue(new Callback<SettingResponse>() {
                @Override
                public void onResponse(@NonNull Call<SettingResponse> call, @NonNull Response<SettingResponse> response) {
                    if (response.isSuccessful()) {
                        Log.d("AppSetting", response.body().getMessage());
                        if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("found")) {
                            SettingList = response.body().getData();
                            cekotp = SettingList.get(0).getIsotp();

                            if (user != null) {
                                if (ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                    enable_location();
                                } else {
                                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                if (ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                    enable_location();
                                } else {
                                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<SettingResponse> call, @NonNull Throwable t) {
                    Log.d("AppSetting", t.getMessage());
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}