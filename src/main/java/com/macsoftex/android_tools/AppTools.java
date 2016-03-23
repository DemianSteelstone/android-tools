package com.macsoftex.android_tools;

import android.content.Context;
import android.content.pm.PackageManager;

import java.util.Locale;

/**
 * Created by alex-v on 10.09.15.
 */
public class AppTools
{
    public static String getAppVersionName(Context context)
    {
        try
        {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static String getAppPackageName(Context context)
    {
        return context.getApplicationContext().getPackageName();
    }

    public static String getAppeName(Context context)
    {
        return context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
    }

    public static String getCurrentLanguageCode()
    {
        return Locale.getDefault().getLanguage();
    }

    public static String getDeviceName() {
        return android.os.Build.MODEL;
    }

    public static String getOSVersion() {
        return android.os.Build.VERSION.RELEASE;
    }
}
