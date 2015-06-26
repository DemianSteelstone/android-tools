package com.macsoftex.android_tools;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.view.Surface;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by alex-v on 30.09.14.
 */
public class ActivityTools
{
    private static int getScreenOrientation(android.app.Activity activity)
    {
        final int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        final int orientation = activity.getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_270)
                return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
            else
                return ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
        }
        else if (orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_90)
                return ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
            else
                return ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
        }

        return ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
    }

    public static boolean isTablet(android.app.Activity activity)
    {
        return (activity.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static void lockOrientation(android.app.Activity activity)
    {
        final int orientation = getScreenOrientation( activity );
        activity.setRequestedOrientation( orientation );
    }

    public static void unlockOrientation(android.app.Activity activity)
    {
        activity.setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED );
    }

    public static void hideKeyboard(android.app.Activity activity)
    {
        final View view = activity.getCurrentFocus();

        if (view != null)
        {
            final InputMethodManager manager = (InputMethodManager)activity.getSystemService( android.app.Activity.INPUT_METHOD_SERVICE );

            if (manager != null)
                manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
