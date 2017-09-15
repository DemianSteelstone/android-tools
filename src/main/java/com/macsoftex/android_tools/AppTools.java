package com.macsoftex.android_tools;

import android.content.Context;
import android.content.pm.PackageManager;

import java.util.Locale;

/**
 * Created by alex-v on 10.09.15.
 */
public class AppTools {
    public static String getAppVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getAppPackageId(Context context) {
        return context.getApplicationContext().getPackageName();
    }

    public static String getAppName(Context context) {
        return context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
    }

    public static String getCurrentLanguageCode() {
        return Locale.getDefault().getLanguage();
    }

    public static String getDeviceName() {
        return android.os.Build.BRAND + " " + android.os.Build.MODEL;
    }

    public static String getOSVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    public static boolean isPackageInstalled(Context context, String packageId) {
        try {
            context.getPackageManager().getPackageInfo(packageId, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }

        return true;
    }
}
