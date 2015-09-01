package com.macsoftex.android_tools;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by alex-v on 13.11.14.
 */
public class MarketTools
{
    public static void openAppPageInMarket(Context ctx, String appPackageID)
    {
        try
        {
            ctx.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageID)));
        }
        catch (android.content.ActivityNotFoundException e)
        {
            e.printStackTrace();

            ctx.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageID)));
        }
    }
}
