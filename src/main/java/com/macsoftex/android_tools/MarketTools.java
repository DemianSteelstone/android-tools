package com.macsoftex.android_tools;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by alex-v on 13.11.14.
 */
public class MarketTools {
    public static void openAppPageInPlayStore(Context context, String appPackageID) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageID)));
        }
        catch (Exception e) {
            e.printStackTrace();

            try {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageID)));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}
