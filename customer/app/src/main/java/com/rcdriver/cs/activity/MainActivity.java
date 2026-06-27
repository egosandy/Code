package com.rcdriver.cs.activity;

import com.rcdriver.cs.utils.LocalStore;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.rcdriver.cs.activity.transfer.PilihTujuanActivity;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.rcdriver.cs.R;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.constants.VersionChecker;
import com.rcdriver.cs.fragment.HistoryFragment;
import com.rcdriver.cs.fragment.HomeFragment;

import com.rcdriver.cs.json.GetFiturResponseJson;
import com.rcdriver.cs.json.SettingResponse;
import com.rcdriver.cs.models.FiturModel;
import com.rcdriver.cs.models.SettingModel;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.utils.Log;
import com.rcdriver.cs.utils.SettingPreference;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private SettingPreference sp;
    public static String apikey;
    private FragmentManager fragmentManager;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private String mLat, mLng, mAlamat;
    private TextView NamaApp;
    private ImageView Notifikasi;
    ChipNavigationBar chipNavigationBar;
    LinearLayout mAdViewLayout;
    LatLng LokasiKu;
    long mBackPressed;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        context = this;
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int success = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (success != ConnectionResult.SUCCESS) {
            googleApiAvailability.makeGooglePlayServicesAvailable(this);
        }
        GetSetting();

        FirebaseMessaging.getInstance().subscribeToTopic("pelanggan");
        //app update

        //enf app update
        sp = new SettingPreference(this);
        mAdViewLayout = findViewById(R.id.adView);
        fragmentManager = getSupportFragmentManager();
        NamaApp = findViewById(R.id.NamaApp);
        Notifikasi = findViewById(R.id.Notifikasi);
        FloatingActionButton promoButton = findViewById(R.id.prom);
//        chipNavigationBar = findViewById(R.id.bawahmenu);



        promoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aksi ketika Floating Action Button ditekan
                Intent intent = new Intent(MainActivity.this, PilihTujuanActivity.class);
                startActivity(intent);
            }
        });

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        if (extras != null) {
            for (String key : extras.keySet()) {
                Object value = extras.get(key);
                Log.d(TAG, "Extras received at onCreate:  Key: " + key + " Value: " + value);
            }
            String title = extras.getString("title");
            String message = extras.getString("body");
            if (message != null && message.length() > 0) {
                getIntent().removeExtra("body");
                showNotificationInADialog(title, message);
            }
        }
        if (savedInstanceState == null) {
            HomeFragment homeFragment = new HomeFragment();
            loadFrag(homeFragment, getString(R.string.menu_home), fragmentManager);
        }
        sp.updateNotif("Null");
        sp.updateTitle("Null");
        //------------------------------------------------------------------------------

        User loginUser = BaseApp.getInstance(this).getLoginUser();
        Constants.TOKEN = loginUser.getToken();
        Constants.USERID = loginUser.getId();


        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        setupGoogleAPI();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.home:
                        fragment = new HomeFragment();
                        break;

                    case R.id.order:
                        fragment = new HistoryFragment();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.Container, fragment).commit();
                return true;
            }
        });

    }

    private void setupGoogleAPI() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient
                    .Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
            mGoogleApiClient.connect();
        }

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            LokasiKu = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            String Lat = String.valueOf(mLastLocation.getLatitude());
            String Lng = String.valueOf(mLastLocation.getLongitude());
            mLat = Lat;
            mLng = Lng;
            try {
                Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1);
                if (addresses != null && addresses.size() > 0) {
                    String address = addresses.get(0).getAddressLine(0);
                    mAlamat = address;
                    Log.d("Lokasiku", "getAddress: " + address);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Check_version();
        mGoogleApiClient.connect();
        update();
        GetSetting();
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int success = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (success != ConnectionResult.SUCCESS) {
            googleApiAvailability.makeGooglePlayServicesAvailable(this);
        }
        FirebaseMessaging.getInstance().subscribeToTopic("pelanggan")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Topic Subscribe";
                        if (!task.isSuccessful()) {
                            msg = "Topic Not Subscribe";
                        }
                        Log.d("FcmTopic", msg);
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onBackPressed() {
        int count = this.getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            if (mBackPressed + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
            } else {
                moveTaskToBack(true);

            }
        } else {
            super.onBackPressed();
        }
    }





    public void loadFrag(Fragment f1, String name, FragmentManager fm) {
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.Container, f1, name);
        ft.commit();
    }


    private void update() {
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        UserService userService = ServiceGenerator.createService(UserService.class,
                loginUser.getEmail(), loginUser.getPassword());
        userService.getFitur().enqueue(new Callback<GetFiturResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<GetFiturResponseJson> call, @NonNull Response<GetFiturResponseJson> response) {
                if (response.isSuccessful()) {
LocalStore.get().saveFitur(Objects.requireNonNull(response.body()).getData());
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetFiturResponseJson> call, @NonNull Throwable t) {

            }
        });
    }

    List<SettingModel> SettingList;

    private void GetSetting() {
        try {
            User login = BaseApp.getInstance(this).getLoginUser();
            UserService service = ServiceGenerator.createService(UserService.class, "admin", "12345");
            service.setting().enqueue(new Callback<SettingResponse>() {
                @Override
                public void onResponse(@NonNull Call<SettingResponse> call, @NonNull Response<SettingResponse> response) {
                    if (response.isSuccessful()) {
                        Log.d("AppSetting", response.body().getMessage());
                        if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("found")) {
                            SettingList = response.body().getData();
                            String getkey = SettingList.get(0).getMapkey();
                            String getMaintenance = SettingList.get(0).getMaintenance();
//                            android.util.Log.e("Tripay", String.valueOf(login.getIstopup()));
                            apikey = getkey;
                            android.util.Log.e("GoogleKey", apikey);
                            //-------------------- Midtrans -------------------------------
                            String getbaseurl = SettingList.get(0).getBaseurl();
                            String getclientkey = SettingList.get(0).getClientkey();
                            String getstatus = SettingList.get(0).getStatus();
                            String getApp = SettingList.get(0).getNamapp();
                            String getKontak = SettingList.get(0).getKontak();
                            if(getApp == null){
                                NamaApp.setText(getString(R.string.app_name));
                            }else{
                                NamaApp.setText(getApp);
                            }


                            //-------------------- MobilePulsa -------------------------------
                            String mobileurl = SettingList.get(0).getMpurl();
                            String mobileuser = SettingList.get(0).getMpuser();
                            String mobilepass = SettingList.get(0).getMppass();
                            String mobilestatus = SettingList.get(0).getMpstatus();

                            android.util.Log.e("PPOB", "Status : " + mobilestatus);
//                            String cekTopup = String.valueOf(login.getIstopup());
                            android.util.Log.e("MIDTRANS", "Url : " + getbaseurl + " Key : " + getclientkey);
                            int versiondb = SettingList.get(0).getVersionCode();
                            Log.e("VERSION", "db:" + versiondb);
                            try{
                                PackageInfo pInfo = MainActivity.this.getPackageManager().getPackageInfo(MainActivity.this.getPackageName(), 0);
                                int versiapp = pInfo.versionCode;
                                Log.e("VERSION", "app:" + versiapp + " db:" + versiondb);

                                if(versiapp < versiondb){
                                    AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.DialogStyle);
                                    alert.setTitle(R.string.app_name)
                                            .setIcon(R.mipmap.ic_launcher)
                                            .setMessage("Please update" + " " + context.getString(R.string.app_name) + " " + "app. you have an old version.")
                                            .setNegativeButton("Update", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName())));
                                                    finish();
                                                }
                                            });

                                    if(SettingList.get(0).getForceUpdate() < 1){
                                        alert.setPositiveButton("Later", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                    }

                                    alert.setCancelable(false);
                                    alert.show();
                                }


                            }catch (Exception e){
                                e.printStackTrace();
                                Log.e("VERSION", e.getMessage());
                            }
                            if (getMaintenance.equals("1")) {
                                String url = Constants.MAINTENANCE_URL;
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                                android.util.Log.e("MAINTENANCE", "Status : " + getMaintenance);
                            } else {
                                android.util.Log.e("MAINTENANCE", "Status : " + getMaintenance);
                                return;
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
    public void Check_version() {
        VersionChecker versionChecker = new VersionChecker(this);
        versionChecker.execute();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            onBackPressed();
        }
        return false;
    }
    @Override
    public void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent - starting");
        Bundle extras = intent.getExtras();
        if (extras != null) {
            for (String key : extras.keySet()) {
                Object value = extras.get(key);
                Log.d(TAG, "Extras received at onNewIntent:  Key: " + key + " Value: " + value);
            }
            String title = extras.getString("title");
            String message = extras.getString("body");
            if (message!=null && message.length()>0) {
                getIntent().removeExtra("body");
                showNotificationInADialog(title, message);
            }
        }
    }


    private void showNotificationInADialog(String title, String message) {
        // show a dialog with the provided title and message
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
