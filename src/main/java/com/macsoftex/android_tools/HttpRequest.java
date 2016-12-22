package com.macsoftex.android_tools;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alex-v on 12.11.14.
 */

public class HttpRequest
{
    public static final String METHOD_DELETE = "DELETE";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_HEAD = "HEAD";
    public static final String METHOD_OPTIONS = "OPTIONS";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_TRACE = "TRACE";

    private HttpURLConnection connection = null;
    private LinkedHashMap<String,String> postParams = new LinkedHashMap<String,String>();

    public HttpRequest(String url)
    {
        try
        {
            URL urlObject = new URL( url );
            this.connection = (HttpURLConnection) urlObject.openConnection();
            this.init();
        }
        catch (IOException e)
        {
            this.connection = null;
            e.printStackTrace();
        }
    }

    public HttpRequest(URL url)
    {
        try
        {
            this.connection = (HttpURLConnection) url.openConnection();
            this.init();
        }
        catch (IOException e)
        {
            this.connection = null;
            e.printStackTrace();
        }
    }

    public HttpRequest setMethod(String method)
    {
        if (this.connection != null)
        {
            try
            {
                this.connection.setRequestMethod( method );
            }
            catch (ProtocolException e)
            {
                e.printStackTrace();
            }
        }

        return this;
    }

    public HttpRequest setMethodDelete()
    {
        return this.setMethod( METHOD_DELETE );
    }

    public HttpRequest setMethodGet()
    {
        return this.setMethod( METHOD_GET );
    }

    public HttpRequest setMethodHead()
    {
        return this.setMethod( METHOD_HEAD );
    }

    public HttpRequest setMethodOptions()
    {
        return this.setMethod( METHOD_OPTIONS );
    }

    public HttpRequest setMethodPost()
    {
        return this.setMethod(METHOD_POST);
    }

    public HttpRequest setMethodPut()
    {
        return this.setMethod(METHOD_PUT);
    }

    public HttpRequest setMethodTrace()
    {
        return this.setMethod(METHOD_TRACE);
    }

    public HttpRequest setHttpHeader(String field, String value)
    {
        if (this.connection != null)
            this.connection.setRequestProperty(field, value);

        return this;
    }

    public HttpRequest setUserAgent(String userAgent)
    {
        return this.setHttpHeader("User-Agent", userAgent);
    }

    public HttpRequest setRangeFrom(int from)
    {
        return this.setHttpHeader("Range", "bytes=" + from + "-");
    }

    public HttpRequest setRangeFrom(int from, int to)
    {
        return this.setHttpHeader("Range", "bytes=" + from + "-" + to);
    }

    public HttpRequest setTimeout(int timeoutMillis)
    {
        if (this.connection != null)
            this.connection.setConnectTimeout( timeoutMillis );

        return this;
    }

    public HttpRequest setPostData(byte[] data)
    {
        if (this.connection != null)
        {
            this.connection.setDoOutput(true);

            OutputStream os = null;

            try
            {
                os = this.connection.getOutputStream();
                os.write( data );
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                if (os != null)
                {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return this;
    }

    public HttpRequest addPostValue(String key, String value)
    {
        this.postParams.put(key, value);

        return this;
    }

    public HttpRequest setFollowRedirectsFlag(boolean followRedirectsFlag)
    {
        if (this.connection != null)
        {
            this.connection.setInstanceFollowRedirects(followRedirectsFlag);
            HttpURLConnection.setFollowRedirects( followRedirectsFlag );
        }

        return this;
    }

    public HttpResponse send()
    {
        if (this.connection == null)
            return null;

        if (this.postParams.size() > 0)
        {
            byte[] postData = this.getPostData("UTF-8");

            if (postData != null)
                this.setPostData( postData );
        }

        try
        {
            this.connection.connect();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        HttpResponse response = new HttpResponse( this.connection );
        this.cancel();

        return response;
    }

    public void sendAsynchronously(final HttpResponseEvent event)
    {
        new AsyncTask<Void, Void, HttpResponse>() {
            @Override
            protected HttpResponse doInBackground(Void... params)
            {
                return HttpRequest.this.send();
            }

            @Override
            protected void onPostExecute(HttpResponse response)
            {
                if (event != null)
                    event.responseDidReceive( response );
            }
        }.execute();
    }

    public void cancel()
    {
        if (this.connection != null)
        {
            try {
                this.connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void init()
    {
        if (this.connection != null)
        {
            this.connection.setUseCaches( false );
            this.setFollowRedirectsFlag( true );
        }
    }

    private byte[] getPostData(String charset)
    {
        StringBuilder postData = new StringBuilder();

        for (Map.Entry<String,String> param : this.postParams.entrySet())
        {
            String key;
            String value;

            try {
                key = URLEncoder.encode(param.getKey(), charset);
                value = URLEncoder.encode(param.getValue(), charset);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                continue;
            }

            if (postData.length() != 0)
                postData.append('&') ;

            postData.append(key);
            postData.append('=');
            postData.append(value);
        }

        byte[] postDataBytes = null;

        try {
            postDataBytes = postData.toString().getBytes( charset );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return postDataBytes;
    }


    public interface HttpResponseEvent
    {
        public void responseDidReceive(HttpResponse response);
    }


    public class HttpResponse
    {
        private byte[] data;
        private Map<String, List<String>> headers;
        private int resposeCode;
        private int contentLength;

        public HttpResponse(HttpURLConnection connection)
        {
            try
            {
                this.data = FileTools.getBytesFromInputStream( connection.getInputStream() );
                this.headers = connection.getHeaderFields();
                this.contentLength = connection.getContentLength();
                this.resposeCode = connection.getResponseCode();
            }
            catch (Exception e)
            {
                e.printStackTrace();

                this.data = null;
                this.headers = null;
                this.contentLength = 0;
                this.resposeCode = 0;
            }
        }

        public byte[] getData()
        {
            return this.data;
        }

        public String getText()
        {
            if (this.data == null)
                return null;

            return new String( this.data );
        }

        public Map<String, List<String>> getHeaders()
        {
            return this.headers;
        }

        public int getContentLength()
        {
            return this.contentLength;
        }

        public int getResposeCode()
        {
            return this.resposeCode;
        }
    }
}


