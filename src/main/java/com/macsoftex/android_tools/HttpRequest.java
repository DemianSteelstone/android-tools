package com.macsoftex.android_tools;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by alex-v on 12.11.14.
 */
public abstract class HttpRequest
{
    public void get(String url)
    {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... urls)
            {
                if (urls.length != 1)
                    return null;

                String response = null;

                try
                {
                    URL url = new URL( urls[0] );
                    response = FileOperations.getStringFromInputStream( url.openStream() );
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                return response;
            }

            @Override
            protected void onPostExecute(String response)
            {
                HttpRequest.this.onResponseReceived( response );
            }
        }.execute( url );
    }

    protected abstract void onResponseReceived(String response);
}
