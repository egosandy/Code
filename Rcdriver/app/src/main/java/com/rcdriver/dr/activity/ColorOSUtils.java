package com.rcdriver.dr.activity;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

public class ColorOSUtils {

    public static boolean isFloatWindowAllowed(Context context) {
        // Belum ada API resmi, asumsikan user perlu diarahkan manual
        return Settings.canDrawOverlays(context);
    }

    public static Intent toOverlayPermissionIntent(Context context, String packageName) {
        Intent intent = new Intent();
        intent.setClassName("com.coloros.safecenter", "com.coloros.safecenter.permission.floatwindow.FloatWindowListActivity");
        return intent;
    }
}

