package com.macsoftex.android_tools;

import android.app.ActivityManager;
import android.os.Process;
import android.content.Context;
import android.content.pm.PackageManager;

import java.util.List;
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

    public static boolean isRemoteProcess(Context context) {
        Context applicationContext = context.getApplicationContext();
        long myPid = (long) Process.myPid();
        String remotePackageName = getAppPackageId(context) + ":remote";
        ActivityManager activityManager = (ActivityManager) applicationContext.getSystemService(Context.ACTIVITY_SERVICE);

        if (activityManager == null)
            return false;

        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();

        if (runningAppProcesses != null && runningAppProcesses.size() != 0) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (((long) runningAppProcessInfo.pid) == myPid && remotePackageName.equals(runningAppProcessInfo.processName))
                    return true;
            }
        }

        return false;
    }

    public static boolean isServiceRunning(Context context, String serviceClassName) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClassName.equals(service.service.getClassName()))
                return true;
        }

        return false;
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
