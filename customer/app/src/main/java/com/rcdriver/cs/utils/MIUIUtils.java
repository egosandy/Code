package com.rcdriver.cs.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.IOException;

public class MIUIUtils {
    private static final String MIUI_V5 = "V5";
    private static final String MIUI_V6 = "V6";

    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    public static boolean isMIUIV5() {
        return getVersionName().equals(MIUI_V5);
    }

    public static String getVersionName() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            return prop.getProperty(KEY_MIUI_VERSION_NAME);
        } catch (IOException e) {
            return "";
        }
    }

    public static Intent toPermissionManager(Context context, String packageName) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        String version = getVersionName();
        if (MIUI_V5.equals(version)) {
            PackageInfo pInfo;
            try {
                pInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            } catch (PackageManager.NameNotFoundException ignored) {
                return null;
            }
            intent.setClassName("com.android.settings", "com.miui.securitycenter.permission.AppPermissionsEditor");
            intent.putExtra("extra_package_uid", pInfo.applicationInfo.uid);
        } else { // MIUI_V6 and above
            final String PKG_SECURITY_CENTER = "com.miui.securitycenter";
            try {
                context.getPackageManager().getPackageInfo(PKG_SECURITY_CENTER, PackageManager.GET_ACTIVITIES);
            } catch (PackageManager.NameNotFoundException ignored) {
                return null;
            }
            intent.setClassName(PKG_SECURITY_CENTER, "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            intent.putExtra("extra_pkgname", packageName);
        }
        return intent;
    }


}