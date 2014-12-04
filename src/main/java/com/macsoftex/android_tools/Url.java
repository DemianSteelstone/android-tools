package com.macsoftex.android_tools;

import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;

import java.io.File;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by alex-v on 25.09.14.
 */
public class Url
{
    public static String getFileNameFromUrl(final String url)
    {
        Uri uri = Uri.parse( url );
        String path = uri.getPath();

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
                return Url.createUrl(baseUri.getScheme(), relativeUri.getAuthority(), relativeUri.getPath(), relativeUri.getEncodedQuery(), relativeUri.getEncodedFragment());
            }
            else
            {
                if (relativeUri.getPath() != null)
                {
                    if ( relativeUri.getPath().substring(0,1).equals("/") )
                    {
                        return Url.createUrl(baseUri.getScheme(), baseUri.getAuthority(), relativeUri.getPath(), relativeUri.getEncodedQuery(), relativeUri.getEncodedFragment());
                    }
                    else
                    {
                        File baseUrlPath = new File( baseUri.getPath() );
                        File newPath = new File(baseUrlPath.getParent(), relativeUri.getPath());

                        return Url.createUrl(baseUri.getScheme(), baseUri.getAuthority(), newPath.getAbsolutePath(), relativeUri.getEncodedQuery(), relativeUri.getEncodedFragment());
                    }
                }
                else
                {
                    if (relativeUri.getEncodedQuery() != null)
                    {
                        return Url.createUrl(baseUri.getScheme(), baseUri.getAuthority(), baseUri.getPath(), relativeUri.getEncodedQuery(), relativeUri.getEncodedFragment());
                    }
                    else if (relativeUri.getEncodedFragment() != null)
                    {
                        return Url.createUrl(baseUri.getScheme(), baseUri.getAuthority(), baseUri.getPath(), null, relativeUri.getEncodedFragment());
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

        return "http://www.google.by/search?q=" + query;
    }
}
