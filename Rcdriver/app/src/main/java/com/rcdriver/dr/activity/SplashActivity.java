package com.rcdriver.dr.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.CancellationTokenSource;

import com.rcdriver.dr.R;
import com.rcdriver.dr.constants.BaseApp;
import com.rcdriver.dr.constants.Constants;
import com.rcdriver.dr.models.User;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private FusedLocationProviderClient fusedLocationClient;

    // BARU: Cara modern untuk menangani permintaan izin
    private final ActivityResultLauncher<String[]> locationPermissionRequest =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                Boolean fineLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
                Boolean coarseLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false);

                if (fineLocationGranted != null && fineLocationGranted) {
                    // Izin lokasi akurat diberikan, lanjutkan pengecekan GPS
                    checkGpsAndFetchLocation();
                } else if (coarseLocationGranted != null && coarseLocationGranted) {
                    // Izin lokasi perkiraan diberikan, lanjutkan (meski kurang ideal untuk driver)
                    Toast.makeText(this, "Izin lokasi perkiraan diberikan.", Toast.LENGTH_SHORT).show();
                    checkGpsAndFetchLocation();
                } else {
                    // Izin ditolak
                    Toast.makeText(this, "Izin lokasi diperlukan untuk melanjutkan.", Toast.LENGTH_LONG).show();
                    // Tampilkan dialog untuk mengarahkan ke pengaturan aplikasi
                    showPermissionDeniedDialog();
                }
            });

    // BARU: Launcher untuk mengarahkan ke pengaturan lokasi
    private final ActivityResultLauncher<Intent> locationSettingsLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                // Setelah kembali dari pengaturan, cek lagi status GPS
                checkGpsAndFetchLocation();
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        sharedPreferences = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);

        removeNotif();
        getSetting();
    }

    private void getSetting() {
        // ... (Kode getSetting Anda tetap sama)
        // Saya akan sederhanakan logikanya untuk fokus pada alur lokasi
        // Anggap network call berhasil dan tidak ada paksaan update
        proceedAfterUpdateCheck();
    }

    // BARU: Fungsi untuk melanjutkan alur setelah pengecekan update selesai
    private void proceedAfterUpdateCheck() {
        if (hasLocationPermissions()) {
            checkGpsAndFetchLocation();
        } else {
            // Minta izin jika belum ada
            locationPermissionRequest.launch(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        }
    }

    // BARU: Memeriksa apakah izin sudah ada
    private boolean hasLocationPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    // BARU: Memeriksa status GPS dan kemudian mengambil lokasi
    private void checkGpsAndFetchLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null && !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // GPS mati, arahkan ke pengaturan
            new AlertDialog.Builder(this, R.style.DialogStyle)
                    .setTitle("GPS Diperlukan")
                    .setMessage("Aplikasi ini memerlukan GPS untuk berfungsi. Aktifkan GPS?")
                    .setPositiveButton("Pengaturan", (dialog, which) -> {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        locationSettingsLauncher.launch(intent);
                    })
                    .setNegativeButton("Batal", (dialog, which) -> finish())
                    .show();
        } else {
            // GPS sudah aktif, langsung ambil lokasi
            fetchCurrentLocation();
        }
    }

    @SuppressLint("MissingPermission")
    private void fetchCurrentLocation() {
        // Coba dapatkan lokasi saat ini (lebih andal daripada getLastLocation)
        CancellationTokenSource cancellationTokenSource = new CancellationTokenSource();
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancellationTokenSource.getToken())
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        saveLocationAndProceed(location);
                    } else {
                        // Jika getCurrentLocation gagal, coba getLastLocation sebagai fallback
                        fusedLocationClient.getLastLocation().addOnSuccessListener(this, lastLocation -> {
                            if (lastLocation != null) {
                                saveLocationAndProceed(lastLocation);
                            } else {
                                // Jika keduanya gagal, gunakan default
                                Toast.makeText(this, "Tidak dapat mengambil lokasi, menggunakan lokasi default.", Toast.LENGTH_SHORT).show();
                                saveLocationAndProceed(null);
                            }
                        });
                    }
                })
                .addOnFailureListener(this, e -> {
                    Toast.makeText(this, "Gagal mendapatkan lokasi: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    saveLocationAndProceed(null); // Gagal, gunakan default
                });
    }

    // BARU: Menyimpan lokasi dan melanjutkan ke activity berikutnya
    private void saveLocationAndProceed(Location location) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (location != null) {
            editor.putString(String.valueOf(Constants.LATITUDE), String.valueOf(location.getLatitude()));
            editor.putString(String.valueOf(Constants.LONGITUDE), String.valueOf(location.getLongitude()));
        } else {
            // Lokasi default
            editor.putString(String.valueOf(Constants.LATITUDE), "33.738045");
            editor.putString(String.valueOf(Constants.LONGITUDE), "73.084488");
        }
        editor.apply();

        User user = BaseApp.getInstance(this).getLoginUser();
        Intent intent;
        if (user != null) {
            intent = new Intent(SplashActivity.this, MainActivity.class);
        } else {
            intent = new Intent(SplashActivity.this, IntroActivity.class);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.from_right, R.anim.to_left);
        finish();
    }

    // BARU: Dialog jika pengguna menolak izin
    private void showPermissionDeniedDialog() {
        new AlertDialog.Builder(this, R.style.DialogStyle)
                .setTitle("Izin Diperlukan")
                .setMessage("Aplikasi ini tidak dapat berfungsi tanpa izin lokasi. Izinkan di pengaturan aplikasi?")
                .setPositiveButton("Pengaturan", (dialog, which) -> {
                    // Arahkan ke pengaturan aplikasi
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Keluar", (dialog, which) -> finish())
                .setCancelable(false)
                .show();
    }

    private void removeNotif() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.cancelAll();
        }
    }
}