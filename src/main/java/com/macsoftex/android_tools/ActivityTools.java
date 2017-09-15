package com.macsoftex.android_tools;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.view.Surface;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by alex-v on 30.09.14.
 */
public class ActivityTools {
    private static int getScreenOrientation(Activity activity) {
        final int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        final int orientation = activity.getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_270)
                return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
            else
                return ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_90)
                return ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
            else
                return ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
        }

        return ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static void lockOrientation(Activity activity) {
        final int orientation = getScreenOrientation(activity);
        activity.setRequestedOrientation(orientation);
    }

    public static void unlockOrientation(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    public static void hideKeyboard(Activity activity) {
        final View view = activity.getCurrentFocus();
        hideKeyboard(activity, view);
    }

    public static void hideKeyboard(Context context, View view) {
        if (view != null) {
            final InputMethodManager manager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);

            if (manager != null)
                manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void showKeyboard(Context context) {
        final InputMethodManager manager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);

        if (manager != null)
            manager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
}
