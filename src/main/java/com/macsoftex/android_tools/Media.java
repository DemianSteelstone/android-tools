package com.macsoftex.android_tools;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

/**
 * Created by alex-v on 30.09.14.
 */
public class Media
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
}
