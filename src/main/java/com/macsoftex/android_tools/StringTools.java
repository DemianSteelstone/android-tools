package com.macsoftex.android_tools;

import android.text.Html;
import android.text.Spanned;

/**
 * Created by alex-v on 16.12.14.
 */
public class StringTools {
    public static String ellipsize(String text, int maxLength) {
        return ellipsize(text, maxLength, "...");
    }

    public static String ellipsize(String text, int maxLength, String ellipStr) {
        if (text == null || text.length() < maxLength)
            return text;

        return text.substring(0, maxLength) + ellipStr;
    }

    public static String addLineToText(String text, String line) {
        if (text==null || text.length()==0)
            return line;

        return text + "\n" + line;
    }

    public static String stringByCapitalizingFirstLetterInString(String str) {
        if (str == null)
            return null;

        if (str.length() > 2)
            return str.substring(0,1).toUpperCase() + str.substring(1);

        return str.toUpperCase();
    }

    public static Spanned fromHtml(String html) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(html);
        }
    }
}
