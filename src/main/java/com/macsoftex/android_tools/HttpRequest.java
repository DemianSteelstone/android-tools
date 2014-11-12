package com.macsoftex.android_tools;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

import java.net.URL;

/**
 * Created by alex-v on 12.11.14.
 */
public abstract class HttpRequest
{
    private String userAgent = null;

    public void setUserAgent(String userAgent)
    {
        this.userAgent = userAgent;
    }

    public void get(String url)
    {
        HttpGet httpGet = new HttpGet( url );
        this.sendRequest( httpGet );
    }

    private void sendRequest(final HttpRequestBase httpMethod)
    {
        if (this.userAgent != null)
            httpMethod.setHeader("User-Agent", this.userAgent);

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params)
            {
                String responseText = null;
                HttpClient httpclient = new DefaultHttpClient();

                try
                {
                    HttpResponse response = httpclient.execute( httpMethod );
                    HttpEntity entity = response.getEntity();

                    if (entity != null)
                        responseText = FileOperations.getStringFromInputStream( entity.getContent() );
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                return responseText;
            }

            @Override
            protected void onPostExecute(String response)
            {
                HttpRequest.this.onResponseReceived( response );
            }
        }.execute();
    }

    protected abstract void onResponseReceived(String response);
}
