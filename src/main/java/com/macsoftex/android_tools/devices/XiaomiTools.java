package com.macsoftex.android_tools.devices;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.text.TextUtils;

import com.macsoftex.android_tools.ActivityTools;
import com.macsoftex.android_tools.AppTools;

/**
 * Created by alex on 14.12.17.
 */

public class XiaomiTools {
    public static boolean isXiaomiModel() {
        final String manufacturer = android.os.Build.MANUFACTURER;
        return manufacturer != null && manufacturer.equalsIgnoreCase("xiaomi");
    }

    public static boolean isMiuiOs() {
        String version = getMiuiVersion();
        return version != null && !TextUtils.isEmpty(version);
    }

    public static String getMiuiVersion() {
        return AppTools.getSystemProperty("ro.miui.ui.version.name");
    }

    public static int getShortMiuiVersion() {
        String version = getMiuiVersion();

        try {
            return Integer.parseInt(version.substring(1));
        } catch (Exception e) {
            return -1;
        }
    }

    public static boolean showAutoStartManagementActivity(Activity activity, int requestCode) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));

        return showActivity(activity, intent, requestCode);
    }

    public static boolean showAppPermissionsEditorActivity(Activity activity, int requestCode) {
        int version = getShortMiuiVersion();

        if (version >=6 && version <= 8) {
            Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            intent.putExtra("extra_pkgname", activity.getPackageName());

            return showActivity(activity, intent, requestCode);
        } else {
            return ActivityTools.showApplicationDetailsSettingsActivity(activity, requestCode);
        }
    }

    public static boolean showPowerHideModeActivity(Activity activity, int requestCode) {
        Intent intent = new Intent();
        intent.setClassName("com.miui.powerkeeper", "com.miui.powerkeeper.ui.PowerHideModeActivity");

        return showActivity(activity, intent, requestCode);
    }

    public static boolean showHiddenAppsContainerManagementActivity(Activity activity, int requestCode) {
        Intent intent = new Intent();
        intent.setClassName("com.miui.powerkeeper", "com.miui.powerkeeper.ui.HiddenAppsContainerManagementActivity");

        return showActivity(activity, intent, requestCode);
    }

    public static boolean showHiddenAppsConfigActivity(Activity activity, int requestCode) {
        Intent intent = new Intent();
        intent.setClassName("com.miui.powerkeeper", "com.miui.powerkeeper.ui.HiddenAppsConfigActivity");
        intent.putExtra("package_name", activity.getPackageName());
        intent.putExtra("package_label", AppTools.getAppName(activity));

        return showActivity(activity, intent, requestCode);
    }

    public static boolean showPowerOptimizeActivity(Activity activity, int requestCode) {
        Intent intent = new Intent();
        intent.setClassName("com.miui.powerkeeper", "com.miui.powerkeeper.ui.PowerOptimizeActivity");

        return showActivity(activity, intent, requestCode);
    }

    public static boolean showAppInvisibleActivity(Activity activity, int requestCode) {
        Intent intent = new Intent();
        intent.setClassName("com.miui.powerkeeper", "com.miui.powerkeeper.ui.AppInvisibleActivity");

        return showActivity(activity, intent, requestCode);
    }

    public static boolean showPowerCheckerManagementActivity(Activity activity, int requestCode) {
        Intent intent = new Intent();
        intent.setClassName("com.miui.powerkeeper", "com.miui.powerkeeper.ui.PowerCheckerManagementActivity");

        return showActivity(activity, intent, requestCode);
    }

    private static boolean showActivity(Activity activity, Intent intent, int requestCode) {
        try {
            activity.startActivityForResult(intent, requestCode);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
