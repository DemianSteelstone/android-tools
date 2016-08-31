package com.macsoftex.android_tools;

/**
 * Created by alex-v on 16.12.14.
 */
public class StringTools
{
    public static String ellipsize(String text, int maxLength)
    {
        return ellipsize(text, maxLength, "...");
    }

    public static String ellipsize(String text, int maxLength, String ellipStr)
    {
        if (text == null || text.length() < maxLength)
            return text;

        return text.substring(0, maxLength) + ellipStr;
    }

    public static String addLineToText(String text, String line) {
        if (text==null || text.length()==0)
            return line;

        return text + "\n" + line;
    }
}
