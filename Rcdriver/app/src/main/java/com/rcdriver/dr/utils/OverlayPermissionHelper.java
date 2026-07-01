package com.rcdriver.dr.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

/**
 * Helper class untuk memeriksa dan meminta izin SYSTEM_ALERT_WINDOW (Draw Over Other Apps).
 * Izin ini diperlukan untuk menampilkan view di atas aplikasi lain, seperti Floating Widget.
 */
public class OverlayPermissionHelper {

    /**
     * Memeriksa apakah aplikasi sudah memiliki izin untuk menampilkan overlay.
     *
     * @param context Context aplikasi.
     * @return true jika izin diberikan, false jika tidak.
     */
    public static boolean hasOverlayPermission(Context context) {
        // Untuk Android M (API 23) ke atas, perlu pengecekan eksplisit.
        // Untuk versi di bawahnya, izin diberikan saat instalasi.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(context);
        }
        return true;
    }

    /**
     * Membuka halaman pengaturan sistem agar pengguna bisa memberikan izin overlay secara manual.
     *
     * @param activity    Activity yang meminta izin.
     * @param requestCode Kode permintaan yang akan digunakan di onActivityResult.
     */
    public static void requestOverlayPermission(Activity activity, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + activity.getPackageName()));
            activity.startActivityForResult(intent, requestCode);
        }
    }
}