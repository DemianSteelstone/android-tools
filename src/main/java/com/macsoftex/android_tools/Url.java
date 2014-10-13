package com.macsoftex.android_tools;

import android.net.Uri;

/**
 * Created by alex-v on 25.09.14.
 */
public class Url
{
    public static String getFileNameExtensionFromUrl(final String url)
    {
        Uri uri = Uri.parse( url );
        String path = uri.getPath();

        if (path.lastIndexOf(".") != -1)
            return path.substring(path.lastIndexOf(".") + 1);

        return null;
    }
}
