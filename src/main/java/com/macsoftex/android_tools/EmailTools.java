package com.macsoftex.android_tools;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import java.io.File;
import java.util.List;

/**
 * Created by alex-v on 23.03.16.
 */
public class EmailTools {
    public static boolean sendEmail(Context context, String email, String subject, String body, File attachmentFile) {
        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setType("message/rfc822");
            intent.setData(Uri.parse("mailto:" + email));
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, body);

            if (attachmentFile != null)
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(attachmentFile));

            List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentActivities(intent, 0);

            if (resolveInfos.size() == 0)
                return false;

            String packageName = resolveInfos.get(0).activityInfo.packageName;
            String name = resolveInfos.get(0).activityInfo.name;

            intent.setAction(Intent.ACTION_SEND);
            intent.setComponent(new ComponentName(packageName, name));

            context.startActivity(intent);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
