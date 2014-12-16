package com.macsoftex.android_tools;

/**
 * Created by alex-v on 16.12.14.
 */
public class StringTools
{
    public static String ellipsize(String input, int maxLength)
    {
        if (input == null || input.length() < maxLength)
            return input;

        return input.substring(0, maxLength) + "...";
    }
}
