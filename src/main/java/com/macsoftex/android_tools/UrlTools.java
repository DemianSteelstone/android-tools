package com.macsoftex.android_tools;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;

import java.io.File;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex-v on 25.09.14.
 */
public class UrlTools {
    private static final String GOOGLE_SEARCH_URL = "http://www.google.by/search?q=";
    private static final String[] LEVEL_1_DOMAINS = {"biz", "com", "edu", "gov", "info", "jobs", "mobi", "name", "net", "org", "pro", "tel", "xxx", "ac", "ad", "ae", "af", "ag", "ai", "al", "am", "an", "ao", "aq", "ar", "as", "at", "au", "aw", "ax", "az", "ba", "bb", "bd", "be", "bf", "bg", "bh", "bi", "bj", "bm", "bn", "bo", "br", "bs", "bt", "bv", "bw", "by", "bz", "ca", "cc", "cd", "cf", "cg", "ch", "ci", "ck", "cl", "cm", "cn", "co", "cr", "cs", "cu", "cv", "cx", "cy", "cz", "de", "dj", "dk", "dm", "do", "dz", "ec", "ee", "eg", "eh", "er", "es", "et", "eu", "fi", "fj", "fk", "fm", "fo", "fr", "ga", "gb", "gd", "ge", "gf", "gg", "gh", "gi", "gl", "gm", "gn", "gp", "gq", "gr", "gs", "gt", "gu", "gw", "gy", "hk", "hm", "hn", "hr", "ht", "hu", "id", "ie", "il", "im", "in", "io", "iq", "ir", "is", "it", "je", "jm", "jo", "jp", "ke", "kg", "kh", "ki", "km", "kn", "kp", "kr", "kw", "ky", "kz", "la", "lb", "lc", "li", "lk", "lr", "ls", "lt", "lu", "lv", "ly", "ma", "mc", "md", "mg", "mh", "mk", "ml", "mm", "mn", "mo", "mp", "mq", "mr", "ms", "mt", "mu", "mv", "mw", "mx", "my", "mz", "na", "nc", "ne", "nf", "ng", "ni", "nl", "no", "np", "nr", "nu", "nz", "om", "pa", "pe", "pf", "pg", "ph", "pk", "pl", "pm", "pn", "pr", "ps", "pt", "pw", "py", "qa", "re", "ro", "ru", "rw", "sa", "sb", "sc", "sd", "se", "sg", "sh", "si", "sj", "sk", "sl", "sm", "sn", "so", "sr", "st", "su", "sv", "sy", "sz", "tc", "td", "tf", "tg", "th", "tj", "tk", "tl", "tm", "tn", "to", "tp", "tr", "tt", "tv", "tw", "tz", "ua", "ug", "uk", "um", "us", "uy", "uz", "va", "vc", "ve", "vg", "vi", "vn", "vu", "wf", "ws", "ye", "yt", "yu", "za", "zm", "zw"};

    public interface ValidHostResultHandler {
        void onValidHostResult(boolean valid);
    }

    public static String getFileNameFromUrl(final String url) {
        Uri uri = Uri.parse(url);
        String path = uri.getPath();

        if (path == null)
            return null;

        return new File(path).getName();
    }

    public static String getFileNameExtensionFromUrl(final String url) {
        return MimeTypeMap.getFileExtensionFromUrl(url);
    }

    public static String getUrlWithRelativeUrl(String baseUrl, String relativeUrl) {
        if (baseUrl==null || baseUrl.length()==0 || !URLUtil.isValidUrl(baseUrl) || relativeUrl==null || relativeUrl.length()==0)
            return null;

        Uri relativeUri = Uri.parse(relativeUrl);

        if (relativeUri.isAbsolute())
            return relativeUri.toString();

        Uri baseUri = Uri.parse(baseUrl);
        Uri.Builder builder = baseUri.buildUpon();

        if (relativeUri.getAuthority() != null)
            builder.authority(relativeUri.getAuthority());

        if (relativeUri.getPath() != null) {
            if (relativeUri.getPath().charAt(0) == '/')
                builder.path(relativeUri.getPath());
            else
                builder.appendPath(relativeUri.getPath());
        }

        if (relativeUri.getQuery() != null)
            builder.query(relativeUri.getQuery());

        if (relativeUri.getFragment() != null)
            builder.fragment(relativeUri.getFragment());

        return builder.toString();
    }

    public static void isValidHost(final String host, final ValidHostResultHandler handler) {
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    InetAddress res = InetAddress.getByName(host);
                    handler.onValidHostResult(true);
                } catch (Exception e) {
                    handler.onValidHostResult(false);
                }
            }
        }).start();
    }

    public static String createGoogleSearchUrl(String searchText) {
        String query;

        try {
            query = URLEncoder.encode(searchText, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return GOOGLE_SEARCH_URL + query;
    }

    public static String getShortHostName(String url) {
        String host = Uri.parse(url).getHost();

        if (host == null)
            return null;

        host = host.toLowerCase();

        String[] parts = host.split("\\.");
        final int n = parts.length;

        if (n < 2)
            return null;

        String lastPart = parts[n-1];
        boolean isInt;

        try {
            Integer.parseInt(lastPart);
            isInt = true;
        } catch(Exception e) {
            isInt = false;
        }

        if (isInt && n>=4) {
            return host;
        } else {
            String level2 = parts[n-2];
            boolean contains = false;

            for (String domain : LEVEL_1_DOMAINS)
                if (level2.equals(domain)) {
                    contains = true;
                    break;
                }

            if (contains && n>=3) {
                return String.format("%s.%s.%s", parts[n-3], parts[n-2], parts[n-1]);
            } else {
                return String.format("%s.%s", parts[n-2], parts[n-1]);
            }
        }
    }

    public static void openUri(Context context, Uri uri) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openUriExceptAppList(Context context, Uri uri, List<String> exceptAppPackageIdList) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);

            PackageManager packageManager = context.getPackageManager();
            List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
            ArrayList<Intent> targetIntents = new ArrayList<Intent>();

            for (ResolveInfo currentInfo : activities) {
                final String packageName = currentInfo.activityInfo.packageName;

                if (! exceptAppPackageIdList.contains(packageName)) {
                    Intent targetIntent = new Intent(Intent.ACTION_VIEW);
                    targetIntent.setData(uri);
                    targetIntent.setPackage(packageName);
                    targetIntents.add(targetIntent);
                }
            }

            if (targetIntents.size() > 0) {
                Intent chooserIntent = Intent.createChooser(targetIntents.remove(0), context.getString( R.string.open_with ));
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetIntents.toArray(new Parcelable[] {}));
                context.startActivity( chooserIntent );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
