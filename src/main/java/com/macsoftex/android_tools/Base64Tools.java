package com.macsoftex.android_tools;

import android.util.Base64;

/**
 * Created by alex-v on 20.05.16.
 */
public class Base64Tools {
    private static final String DEFAULT_ENCODING = "UTF-8";

    public static String encode(String text) {
        try {
            return Base64.encodeToString(text.getBytes(DEFAULT_ENCODING), Base64.NO_WRAP);
        } catch (Exception e) {
            return null;
        }
    }

    public static String decode(String text) {
        try {
            return new String(Base64.decode(text, Base64.NO_WRAP), DEFAULT_ENCODING);
        } catch (Exception e) {
            return null;
        }
    }
}
