package com.macsoftex.android_tools;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

/**
 * Created by alex-v on 30.09.14.
 */
public class MediaTools
{
    public static boolean playVideoWithApp(Uri uri, String appPackageName, Context context)
    {
        try
        {
            Intent intent = new Intent( Intent.ACTION_VIEW );
            intent.setDataAndType(uri, "video/*");
            intent.setPackage(appPackageName);
            context.startActivity(intent);

            return true;
        }
        catch (ActivityNotFoundException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public static void playVideoWithOtherApp(Uri uri, Context context)
    {
        try
        {
            Intent intent = new Intent( Intent.ACTION_VIEW );
            intent.setDataAndType(uri, "video/*");
            context.startActivity(intent);
        }
        catch (ActivityNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static void openImageWithOtherApp(Uri uri, Context context)
    {
        try
        {
            Intent intent = new Intent( Intent.ACTION_VIEW );
            intent.setDataAndType(uri, "image/*");
            context.startActivity(intent);
        }
        catch (ActivityNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
