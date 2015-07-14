package com.macsoftex.android_tools;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;

import java.io.File;
import java.net.InetAddress;
import java.net.URLEncoder;

/**
 * Created by alex-v on 25.09.14.
 */
public class UrlTools
{
    private static final String GOOGLE_SEARCH_URL = "http://www.google.by/search?q=";

    private static final String[] LEVEL_1_DOMAINS = {"biz", "com", "edu", "gov", "info", "jobs", "mobi", "name", "net", "org", "pro", "tel", "xxx", "ac", "ad", "ae", "af", "ag", "ai", "al", "am", "an", "ao", "aq", "ar", "as", "at", "au", "aw", "ax", "az", "ba", "bb", "bd", "be", "bf", "bg", "bh", "bi", "bj", "bm", "bn", "bo", "br", "bs", "bt", "bv", "bw", "by", "bz", "ca", "cc", "cd", "cf", "cg", "ch", "ci", "ck", "cl", "cm", "cn", "co", "cr", "cs", "cu", "cv", "cx", "cy", "cz", "de", "dj", "dk", "dm", "do", "dz", "ec", "ee", "eg", "eh", "er", "es", "et", "eu", "fi", "fj", "fk", "fm", "fo", "fr", "ga", "gb", "gd", "ge", "gf", "gg", "gh", "gi", "gl", "gm", "gn", "gp", "gq", "gr", "gs", "gt", "gu", "gw", "gy", "hk", "hm", "hn", "hr", "ht", "hu", "id", "ie", "il", "im", "in", "io", "iq", "ir", "is", "it", "je", "jm", "jo", "jp", "ke", "kg", "kh", "ki", "km", "kn", "kp", "kr", "kw", "ky", "kz", "la", "lb", "lc", "li", "lk", "lr", "ls", "lt", "lu", "lv", "ly", "ma", "mc", "md", "mg", "mh", "mk", "ml", "mm", "mn", "mo", "mp", "mq", "mr", "ms", "mt", "mu", "mv", "mw", "mx", "my", "mz", "na", "nc", "ne", "nf", "ng", "ni", "nl", "no", "np", "nr", "nu", "nz", "om", "pa", "pe", "pf", "pg", "ph", "pk", "pl", "pm", "pn", "pr", "ps", "pt", "pw", "py", "qa", "re", "ro", "ru", "rw", "sa", "sb", "sc", "sd", "se", "sg", "sh", "si", "sj", "sk", "sl", "sm", "sn", "so", "sr", "st", "su", "sv", "sy", "sz", "tc", "td", "tf", "tg", "th", "tj", "tk", "tl", "tm", "tn", "to", "tp", "tr", "tt", "tv", "tw", "tz", "ua", "ug", "uk", "um", "us", "uy", "uz", "va", "vc", "ve", "vg", "vi", "vn", "vu", "wf", "ws", "ye", "yt", "yu", "za", "zm", "zw"};

    public static String getFileNameFromUrl(final String url)
    {
        Uri uri = Uri.parse( url );
        String path = uri.getPath();

        if (path == null)
            return null;

        return new File( path ).getName();
    }

    public static String getFileNameExtensionFromUrl(final String url)
    {
        return MimeTypeMap.getFileExtensionFromUrl( url );
    }

    private static String createUrl(String scheme, String host, String path, String query, String fragment)
    {
        StringBuffer url = new StringBuffer();

        if (scheme != null)
        {
            url.append( scheme );
            url.append(":");
        }

        if (host != null)
        {
            url.append("//");
            url.append( host );
        }

        if (path != null)
        {
            url.append( path );
        }

        if (query != null)
        {
            url.append("?");
            url.append( query );
        }

        if (fragment != null)
        {
            url.append("#");
            url.append( fragment );
        }

        return url.toString();
    }

    public static String getUrlWithRelativeUrl(String baseUrl, String relativeUrl)
    {
        if (baseUrl==null || baseUrl.length()==0 || !URLUtil.isValidUrl(baseUrl) || relativeUrl==null || relativeUrl.length()==0)
            return null;

        if ( URLUtil.isValidUrl( relativeUrl ) )
            return relativeUrl;

        Uri relativeUri = Uri.parse( relativeUrl );
        Uri baseUri = Uri.parse( baseUrl );

        if (relativeUri.getScheme() == null)
        {
            if (relativeUri.getHost() != null)
            {
                return UrlTools.createUrl(baseUri.getScheme(), relativeUri.getAuthority(), relativeUri.getPath(), relativeUri.getEncodedQuery(), relativeUri.getEncodedFragment());
            }
            else
            {
                if (relativeUri.getPath() != null)
                {
                    if ( relativeUri.getPath().substring(0,1).equals("/") )
                    {
                        return UrlTools.createUrl(baseUri.getScheme(), baseUri.getAuthority(), relativeUri.getPath(), relativeUri.getEncodedQuery(), relativeUri.getEncodedFragment());
                    }
                    else
                    {
                        File baseUrlPath = new File( baseUri.getPath() );
                        File newPath = new File(baseUrlPath.getParent(), relativeUri.getPath());

                        return UrlTools.createUrl(baseUri.getScheme(), baseUri.getAuthority(), newPath.getAbsolutePath(), relativeUri.getEncodedQuery(), relativeUri.getEncodedFragment());
                    }
                }
                else
                {
                    if (relativeUri.getEncodedQuery() != null)
                    {
                        return UrlTools.createUrl(baseUri.getScheme(), baseUri.getAuthority(), baseUri.getPath(), relativeUri.getEncodedQuery(), relativeUri.getEncodedFragment());
                    }
                    else if (relativeUri.getEncodedFragment() != null)
                    {
                        return UrlTools.createUrl(baseUri.getScheme(), baseUri.getAuthority(), baseUri.getPath(), null, relativeUri.getEncodedFragment());
                    }
                }
            }
        }

        return null;
    }

    public interface ValidHostResultEvent
    {
        public void validHostResult(boolean valid);
    }

    public static void isValidHost(final String host, final ValidHostResultEvent handler)
    {
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    InetAddress res = InetAddress.getByName( host );
                    handler.validHostResult( true );
                }
                catch (Exception e)
                {
                    handler.validHostResult( false );
                }
            }
        }).start();
    }

    public static String getGoogleSearchUrl(String searchText)
    {
        String query;

        try
        {
            query = URLEncoder.encode(searchText, "utf-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

        return GOOGLE_SEARCH_URL + query;
    }

    public static String getShortHostName(String url)
    {
        String host = Uri.parse( url ).getHost();

        if (host == null)
            return null;

        host = host.toLowerCase();

        String[] parts = host.split("\\.");
        final int n = parts.length;

        if (n < 2)
            return null;

        String lastPart = parts[n-1];
        boolean isInt;

        try
        {
            Integer.parseInt( lastPart );
            isInt = true;
        }
        catch(NumberFormatException nfe)
        {
            isInt = false;
        }

        if (isInt && n>=4)
        {
            return host;
        }
        else
        {
            String level2 = parts[n-2];
            boolean contains = false;

            for (String domain : LEVEL_1_DOMAINS)
                if ( level2.equals( domain ) )
                {
                    contains = true;
                    break;
                }

            if (contains && n>=3)
            {
                return String.format("%s.%s.%s", parts[n - 3], parts[n - 2], parts[n - 1]);
            }
            else
            {
                return String.format("%s.%s", parts[n - 2], parts[n - 1]);
            }
        }
    }

    public static void openUriInBrowser(Uri uri, Context ctx)
    {
        try
        {
            Intent intent = new Intent( Intent.ACTION_VIEW );
            intent.setData( uri );
            ctx.startActivity( intent );
        }
        catch (ActivityNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
