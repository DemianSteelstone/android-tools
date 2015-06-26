package com.macsoftex.android_tools;

/**
 * Created by alex-v on 16.12.14.
 */
public class StringTools
{
    public static java.lang.String ellipsize(java.lang.String str, int maxLength)
    {
        if (str == null || str.length() < maxLength)
            return str;

        return str.substring(0, maxLength) + "...";
    }
}
