package com.macsoftex.android_tools;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by alex-v on 13.11.14.
 */
public class Market
{
    public static void openApplicationPageInGooglePlayMarket(Context ctx, String appPackageName)
    {
        try
        {
            ctx.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
        }
        catch (android.content.ActivityNotFoundException e)
        {
            e.printStackTrace();

        }
    }
}
