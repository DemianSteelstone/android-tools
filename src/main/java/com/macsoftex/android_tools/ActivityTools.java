package com.macsoftex.android_tools;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.view.Surface;

/**
 * Created by alex-v on 30.09.14.
 */
public class ActivityTools
{
    public static boolean isTablet(Activity activity)
    {
        return (activity.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static void lockOrientation(Activity activity)
    {
        int orientation = getScreenOrientation( activity );
        activity.setRequestedOrientation( orientation );
    }

    public static void unlockOrientation(Activity activity)
    {
        activity.setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED );
    }

    private static int getScreenOrientation(Activity activity)
    {
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int orientation = activity.getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_270)
                return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
            else
                return ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
        }

        if (orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            int landscape = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
            int landscape_reverse = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;

            if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_90)
                return landscape;
            else
                return landscape_reverse;
        }

        return ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
    }
}
