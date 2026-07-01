package com.rcdriver.dr.activity;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

public class EMUIUtils {

    public static boolean isFloatWindowAllowed(Context context) {
        // EMUI umumnya mengizinkan tapi bisa dicek lewat sistem juga
        return Settings.canDrawOverlays(context);
    }

    public static Intent toOverlayPermissionIntent(Context context) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(new android.content.ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity"));
        return intent;
    }
}

