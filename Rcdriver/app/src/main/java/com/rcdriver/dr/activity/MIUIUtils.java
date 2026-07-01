package com.rcdriver.dr.activity;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;

public class MIUIUtils {

    public static boolean isFloatWindowOptionAllowed(Context context) {
        try {
            AppOpsManager appOpsMgr = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOpsMgr.checkOpNoThrow("android:system_alert_window",
                    android.os.Process.myUid(), context.getPackageName());
            return mode == AppOpsManager.MODE_ALLOWED;
        } catch (Exception e) {
            return false;
        }
    }

    public static Intent toFloatWindowPermission(Context context, String packageName) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
        intent.putExtra("extra_pkgname", packageName);
        return intent;
    }
}
