// LOKASI: com.asia.pengemudi.activity.MainActivity.java
package com.rcdriver.dr.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.rcdriver.dr.R;
import com.rcdriver.dr.activity.payment.TopupOnlineFragment;
import com.rcdriver.dr.constants.BaseApp;
import com.rcdriver.dr.constants.Constants;
import com.rcdriver.dr.fragment.HomeFragment;
import com.rcdriver.dr.fragment.OrderFragment;
import com.rcdriver.dr.fragment.ProfileFragment;
import com.rcdriver.dr.fragment.RiwayatFragment;
import com.rcdriver.dr.fragment.SaldoOutFragment;
import com.rcdriver.dr.json.GetHomeRequestJson;
import com.rcdriver.dr.json.GetHomeResponseJson;
import com.rcdriver.dr.json.LoginRequest;
import com.rcdriver.dr.json.LoginResponse;
import com.rcdriver.dr.json.ResponseJson;
import com.rcdriver.dr.json.SaveStatusRequest;
import com.rcdriver.dr.json.SaveStatusResponse;
import com.rcdriver.dr.json.SettingResponse;
import com.rcdriver.dr.json.UpdateLoginRequest;
import com.rcdriver.dr.models.LoginModel;
import com.rcdriver.dr.models.SettingModel;
import com.rcdriver.dr.models.TransaksiModel;
import com.rcdriver.dr.models.User;
import com.rcdriver.dr.service.Restarter;
import com.rcdriver.dr.utils.MyLocationService;
import com.rcdriver.dr.utils.OverlayPermissionHelper;
import com.rcdriver.dr.utils.SettingPreference;
import com.rcdriver.dr.utils.api.ServiceGenerator;
import com.rcdriver.dr.utils.api.service.DriverService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static Boolean isWorking;
    public static String apikey = "0";
    private long mBackPressed;
    private SettingPreference sp;
    private RelativeLayout rlprogress;
    private boolean canceled;
    private ChipNavigationBar chipNavigationBar;
    private FragmentManager fragmentManager;

    // BroadcastReceiver sudah dihapus dari sini

    private final ActivityResultLauncher<String[]> permissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), (Map<String, Boolean> result) -> {
                Boolean fineLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
                boolean notificationGranted = (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) || result.getOrDefault(Manifest.permission.POST_NOTIFICATIONS, false);

                if (fineLocationGranted) {
                    startMyLocationService();
                } else {
                    Toast.makeText(this, "Izin lokasi diperlukan untuk fitur pelacakan.", Toast.LENGTH_LONG).show();
                    showPermissionDeniedDialog();
                }

                if (!notificationGranted) {
                    Toast.makeText(this, "Izin notifikasi tidak diberikan, Anda mungkin tidak menerima order.", Toast.LENGTH_LONG).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chipNavigationBar = findViewById(R.id.navigation);
        rlprogress = findViewById(R.id.rlprogress);
        fragmentManager = getSupportFragmentManager();
        sp = new SettingPreference(this);
        canceled = false;

        // Inisialisasi BroadcastReceiver sudah dihapus

        checkAndRequestPermissions();
        checkOverlayPermission();

        FirebaseMessaging.getInstance().subscribeToTopic("driver");
        setAppVersion();
        setupBottomNavigation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // registerReceiver sudah dihapus
        new Handler(Looper.getMainLooper()).postDelayed(this::gethome, 500);
        new Handler(Looper.getMainLooper()).postDelayed(this::getSetting, 1000);
        new Handler(Looper.getMainLooper()).postDelayed(this::CekLogin, 5000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // unregisterReceiver sudah dihapus
    }

    // ... SEMUA METODE LAINNYA DI BAWAH INI TETAP SAMA SEPERTI KODE ANDA ...

    private void checkAndRequestPermissions() {
        List<String> permissionsToRequest = new ArrayList<>();
        permissionsToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissionsToRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionsToRequest.add(Manifest.permission.POST_NOTIFICATIONS);
        }

        List<String> permissionsNeeded = new ArrayList<>();
        for (String permission : permissionsToRequest) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(permission);
            }
        }

        if (!permissionsNeeded.isEmpty()) {
            permissionLauncher.launch(permissionsNeeded.toArray(new String[0]));
        } else {
            startMyLocationService();
        }
    }

    private void checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            OverlayPermissionHelper.requestOverlayPermission(this, 1234);
        }
    }

    private void startMyLocationService() {
        Intent serviceIntent = new Intent(this, MyLocationService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(this, serviceIntent);
        } else {
            startService(serviceIntent);
        }
        Log.d(TAG, "MyLocationService started.");
    }

    private void setAppVersion() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            Constants.versionname = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setupBottomNavigation() {
        if (fragmentManager.findFragmentById(R.id.Container) == null) {
            chipNavigationBar.setItemSelected(R.id.home, true);
            loadFrag(new HomeFragment(), getString(R.string.menu_home), new TransaksiModel(), "0", "1", false, "0");
        }

        chipNavigationBar.setOnItemSelectedListener(i -> {
            User currentUser = BaseApp.getInstance(this).getLoginUser();
            if (currentUser == null) {
                Toast.makeText(this, "Sesi pengguna tidak valid.", Toast.LENGTH_SHORT).show();
                logout();
                return;
            }

            Fragment fragment = null;
            if (i == R.id.home) {
                canceled = false;
                gethome();
                return;
            }
            if (i == R.id.order) {
                canceled = true;
                fragment = new RiwayatFragment();
            } else if (i == R.id.topup) {
                canceled = true;
                fragment = new TopupOnlineFragment();
            } else if (i == R.id.saldo) {
                canceled = true;
                fragment = new SaldoOutFragment();
            } else if (i == R.id.profile) {
                canceled = true;
                fragment = new ProfileFragment();
            }

            if (fragment != null && !isFinishing()) {
                fragmentManager.beginTransaction().replace(R.id.Container, fragment).commit();
            }
        });
    }

    private void gethome() {
        rlprogress.setVisibility(View.VISIBLE);
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        if (loginUser == null) {
            logout();
            return;
        }

        DriverService userService = ServiceGenerator.createService(DriverService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        GetHomeRequestJson param = new GetHomeRequestJson();
        param.setId(loginUser.getId());
        param.setPhone(loginUser.getNoTelepon());

        userService.home(param).enqueue(new Callback<GetHomeResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<GetHomeResponseJson> call, @NonNull Response<GetHomeResponseJson> response) {
                rlprogress.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    if ("success".equalsIgnoreCase(response.body().getMessage())) {
                        updateConstantsAndPreferences(response.body());
                        handleDriverStatus(response.body());
                        if (response.body().getDatadriver() != null && !response.body().getDatadriver().isEmpty()) {
                            saveUser(response.body().getDatadriver().get(0));
                        }
                        updateWalletBalanceInDb(response.body().getSaldo());
                    } else {
                        Toast.makeText(MainActivity.this, "Akun Anda ditangguhkan.", Toast.LENGTH_SHORT).show();
                        logout();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Gagal menyambung ke server, coba lagi.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetHomeResponseJson> call, @NonNull Throwable t) {
                rlprogress.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Koneksi internet bermasalah.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateConstantsAndPreferences(GetHomeResponseJson body) {
        Constants.CURRENCY = body.getCurrency();
        sp.updateCurrency(body.getCurrency());
        sp.updateabout(body.getAboutus());
        sp.updateemail(body.getEmail());
        sp.updatephone(body.getPhone());
        sp.updateweb(body.getWebsite());
        sp.updateMinTransfer(body.getMinTransfer());
        sp.updateMinWallet(body.getMinWallet());
        sp.updateWasap(body.getSenderWasap());
    }

    private void handleDriverStatus(GetHomeResponseJson body) {
        if (canceled) return;
        String driverStatus = body.getDriverstatus();
        if (("3".equals(driverStatus) || "2".equals(driverStatus)) && body.getTransaksi() != null && !body.getTransaksi().isEmpty()) {
            TransaksiModel transaksi = body.getTransaksi().get(0);
            chipNavigationBar.setVisibility(View.GONE);
            loadFrag(new OrderFragment(), getString(R.string.menu_home), transaksi, body.getSaldo(), driverStatus, true, body.getPoint());
        } else {
            TransaksiModel fakeTransaksi = new TransaksiModel();
            chipNavigationBar.setVisibility(View.VISIBLE);
            loadFrag(new HomeFragment(), getString(R.string.menu_home), fakeTransaksi, body.getSaldo(), "1", false, body.getPoint());
            updateStatusToServer("1");
        }
    }

    private void updateWalletBalanceInDb(String newBalance) {
        User user = BaseApp.getInstance(this).getLoginUser();
        if (user == null) return;
        String userId = user.getId();
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransactionAsync(r -> {
                User realmUser = r.where(User.class).equalTo("id", userId).findFirst();
                if (realmUser != null) {
                    try {
                        realmUser.setWalletSaldo(Long.parseLong(newBalance));
                    } catch (NumberFormatException e) {
                        Log.e(TAG, "Format saldo tidak valid: " + newBalance);
                    }
                }
            });
        }
    }

    private void updateStatusToServer(String status) {
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        if (loginUser == null) return;

        DriverService service = ServiceGenerator.createService(DriverService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        SaveStatusRequest request = new SaveStatusRequest();
        request.setId(loginUser.getId());
        request.setStatus(status);
        service.updateStatus(request).enqueue(new Callback<SaveStatusResponse>() {
            @Override
            public void onResponse(@NonNull Call<SaveStatusResponse> call, @NonNull Response<SaveStatusResponse> response) {
                if (response.isSuccessful() && response.body() != null && "success".equals(response.body().mesage)) {
                    Log.d("SaveStatus", "Status berhasil diupdate ke server: " + status);
                }
            }
            @Override
            public void onFailure(@NonNull Call<SaveStatusResponse> call, @NonNull Throwable t) {
                Log.e("SaveStatus", "Gagal update status", t);
            }
        });
    }

    private void saveUser(User user) {
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransactionAsync(r -> r.copyToRealmOrUpdate(user),
                    () -> {
                        BaseApp.getInstance(MainActivity.this).setLoginUser(user);
                        Log.d(TAG, "Data user berhasil disimpan.");
                    },
                    error -> Log.e(TAG, "Gagal menyimpan data user", error));
        }
    }

    public void loadFrag(Fragment f1, String name, TransaksiModel transaksi, String saldo, String status, boolean setWorking, String point) {
        MainActivity.isWorking = setWorking;
        Log.d(TAG, "STATUS KERJA DISET KE: " + isWorking);

        if (isFinishing() || fragmentManager.isStateSaved()) return;

        Bundle args = new Bundle();
        args.putString("id_pelanggan", transaksi.getIdPelanggan());
        args.putString("id_transaksi", transaksi.getId());
        args.putString("response", String.valueOf(transaksi.status));
        args.putString("saldo", saldo);
        args.putString("point", point);
        args.putString("status", status);
        args.putDouble("AsalLat", transaksi.getStartLatitude());
        args.putDouble("AsalLng", transaksi.getStartLongitude());
        args.putDouble("TujuanLat", transaksi.getEndLatitude());
        args.putDouble("TujuanLng", transaksi.getEndLongitude());
        args.putBoolean("IsWorking", setWorking);

        f1.setArguments(args);
        fragmentManager.beginTransaction().replace(R.id.Container, f1, name).commitAllowingStateLoss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent broadcastIntent = new Intent(this, Restarter.class);
        broadcastIntent.setAction("restartservice");
        sendBroadcast(broadcastIntent);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            if (mBackPressed + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
            } else {
                Toast.makeText(this, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();
                mBackPressed = System.currentTimeMillis();
            }
        } else {
            super.onBackPressed();
        }
    }

    private void showPermissionDeniedDialog() {
        new AlertDialog.Builder(this, R.style.DialogStyle)
                .setTitle("Izin Diperlukan")
                .setMessage("Aplikasi ini memerlukan izin lokasi untuk berfungsi. Izinkan di pengaturan aplikasi?")
                .setPositiveButton("Pengaturan", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                })
                .setNegativeButton("Keluar", (dialog, which) -> finish())
                .setCancelable(false)
                .show();
    }

    private void getSetting() {
        DriverService service = ServiceGenerator.createService(DriverService.class, "admin", "12345");
        service.setting().enqueue(new Callback<SettingResponse>() {
            @Override
            public void onResponse(@NonNull Call<SettingResponse> call, @NonNull Response<SettingResponse> response) {
                if (response.isSuccessful() && response.body() != null && "found".equalsIgnoreCase(response.body().getMessage())) {
                    List<SettingModel> settingList = response.body().getData();
                    if (settingList != null && !settingList.isEmpty()) {
                        SettingModel settings = settingList.get(0);
                        apikey = settings.getMapkey();
                        Constants.URL_MIDTRANS = settings.getBaseurl();
                        Constants.KEY_MIDTRANS = settings.getClientkey();
                        Constants.STATUS_MIDTRANS = settings.getStatus();
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<SettingResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "Gagal mendapatkan setting", t);
            }
        });
    }

    private void CekLogin() {
        User user = BaseApp.getInstance(this).getLoginUser();
        if (user == null) return;

        DriverService driverService = ServiceGenerator.createService(DriverService.class, user.getNoTelepon(), user.getPassword());
        LoginRequest param = new LoginRequest();
        param.setId(user.getId());
        driverService.CekLogin(param).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<LoginModel> loginModels = response.body().getData();
                    if (loginModels != null && !loginModels.isEmpty() && loginModels.get(0).getIslogin() == 0) {
                        Toast.makeText(MainActivity.this, "Akun ini telah dikeluarkan.", Toast.LENGTH_LONG).show();
                        logoutpaksa();
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "CekLogin gagal", t);
            }
        });
    }

    private void logoutpaksa() {
        User driver = BaseApp.getInstance(this).getLoginUser();
        if (driver == null) return;

        DriverService service = ServiceGenerator.createService(DriverService.class, driver.getNoTelepon(), driver.getPassword());
        UpdateLoginRequest param = new UpdateLoginRequest();
        param.setId(driver.getId());
        param.setIslogin(0);
        service.updatelogin(param).enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<ResponseJson> call, @NonNull Response<ResponseJson> response) {
                logout();
            }

            @Override
            public void onFailure(@NonNull Call<ResponseJson> call, @NonNull Throwable t) {
                logout();
            }
        });
    }

    private void logout() {
        stopService(new Intent(this, MyLocationService.class));
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.cancelAll();
        }

        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(r -> r.delete(User.class));
        }
        BaseApp.getInstance(this).setLoginUser(null);
        Intent intent = new Intent(this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}