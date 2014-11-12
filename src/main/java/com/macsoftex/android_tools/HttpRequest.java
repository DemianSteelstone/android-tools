package com.macsoftex.android_tools;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by alex-v on 12.11.14.
 */
public abstract class HttpRequest extends AsyncTask<String, Void, String>
{
    @Override
    protected String doInBackground(String... urls)
    {
        if (urls.length != 1)
            return null;

        String response = null;

        try
        {
            StringBuffer stringBuffer = new StringBuffer();
            URL url = new URL( urls[0] );
            BufferedReader in = new BufferedReader( new InputStreamReader( url.openStream() ) );
            String inputLine;

            while ((inputLine = in.readLine()) != null)
                stringBuffer.append(inputLine + "\n");

            in.close();
            response = stringBuffer.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return response;
    }

    public abstract void onResponseReceived(String response);

    @Override
    protected void onPostExecute(String response)
    {
        this.onResponseReceived( response );
    }
}
