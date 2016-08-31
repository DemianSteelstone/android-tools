package com.macsoftex.android_tools;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by alex-v on 29.08.16.
 */
public class NetworkTools {
    public static boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo().isConnectedOrConnecting();
        } catch (Exception e) {
            return false;
        }
    }
}
