package com.rcdriver.dr.activity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

public class OverlayPermissionHelper {

    private static final String TAG = "OverlayPermissionHelper";

    public static boolean hasOverlayPermission(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return true;
        }

        if (isMIUI()) {
            return MIUIUtils.isFloatWindowOptionAllowed(context);
        }

        if (isColorOS()) {
            return ColorOSUtils.isFloatWindowAllowed(context);
        }

        if (isEMUI()) {
            return EMUIUtils.isFloatWindowAllowed(context);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(context);
        }

        return true;
    }

    public static void requestOverlayPermission(Context context, int requestCode) {
        Intent intent;

        if (isMIUI()) {
            intent = MIUIUtils.toFloatWindowPermission(context, context.getPackageName());
        } else if (isColorOS()) {
            intent = ColorOSUtils.toOverlayPermissionIntent(context, context.getPackageName());
        } else if (isEMUI()) {
            intent = EMUIUtils.toOverlayPermissionIntent(context);
        } else {
            intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + context.getPackageName()));
        }

        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, requestCode);
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    // Brand checker
    private static boolean isMIUI() {
        return Build.MANUFACTURER.toLowerCase().contains("xiaomi");
    }

    private static boolean isColorOS() {
        String brand = Build.BRAND.toLowerCase();
        return brand.contains("oppo") || brand.contains("realme");
    }

    private static boolean isEMUI() {
        return Build.MANUFACTURER.toLowerCase().contains("huawei") || Build.MANUFACTURER.toLowerCase().contains("honor");
    }
}