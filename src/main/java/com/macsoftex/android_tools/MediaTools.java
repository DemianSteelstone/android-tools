package com.macsoftex.android_tools;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by alex-v on 30.09.14.
 */
public class MediaTools
{
    public static void playVideoWithOtherApp(Uri uri, Context ctx)
    {
        try
        {
            Intent i = new Intent( Intent.ACTION_VIEW );
            i.setDataAndType(uri, "video/*");
            ctx.startActivity( i );
        }
        catch (ActivityNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static void openImageWithOtherApp(Uri uri, Context ctx)
    {
        try
        {
            Intent i = new Intent( Intent.ACTION_VIEW );
            i.setDataAndType(uri, "image/*");
            ctx.startActivity( i );
        }
        catch (ActivityNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
