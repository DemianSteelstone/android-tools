package com.macsoftex.android_tools;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by alex-v on 30.09.14.
 */
public class MediaTools {
    public static boolean openVideo(Context context, Uri uri) {
        try {
            Intent intent = createActionViewIntent(context, uri, "video/*");
            context.startActivity(intent);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean openVideoWithApp(Context context, Uri uri, String appPackageID) {
        try {
            Intent intent = createActionViewIntent(context, uri, "video/*");
            intent.setPackage(appPackageID);
            context.startActivity(intent);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean openImage(Context context, Uri uri) {
        try {
            Intent intent = createActionViewIntent(context, uri, "image/*");
            context.startActivity(intent);

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static Intent createActionViewIntent(Context context, Uri uri, String type) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, type);

        return intent;
    }
}
