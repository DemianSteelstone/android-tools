package com.macsoftex.android_tools.http;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by alex-v on 12.11.14.
 */

public class HttpRequest {
    public static final String METHOD_DELETE = "DELETE";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_HEAD = "HEAD";
    public static final String METHOD_OPTIONS = "OPTIONS";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_TRACE = "TRACE";

    private HttpURLConnection connection = null;
    private LinkedHashMap<String,String> postParams = new LinkedHashMap<String,String>();

    public interface HttpResponseHandler {
        void onResponseDidReceive(HttpResponse response);
    }

    public HttpRequest(URL url) {
        init(url);
    }

    public HttpRequest(String strUrl) {
        URL url = null;

        try {
            url = new URL(strUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (url != null)
            init(url);
    }

    public HttpRequest setMethod(String method) {
        if (connection != null) {
            try {
                connection.setRequestMethod(method);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return this;
    }

    public HttpRequest setMethodDelete() {
        return setMethod(METHOD_DELETE);
    }

    public HttpRequest setMethodGet() {
        return setMethod(METHOD_GET);
    }

    public HttpRequest setMethodHead() {
        return setMethod(METHOD_HEAD);
    }

    public HttpRequest setMethodOptions() {
        return setMethod(METHOD_OPTIONS);
    }

    public HttpRequest setMethodPost() {
        return setMethod(METHOD_POST);
    }

    public HttpRequest setMethodPut() {
        return setMethod(METHOD_PUT);
    }

    public HttpRequest setMethodTrace() {
        return setMethod(METHOD_TRACE);
    }

    public HttpRequest setHttpHeader(String field, String value) {
        if (connection != null)
            connection.setRequestProperty(field, value);

        return this;
    }

    public HttpRequest setUserAgent(String userAgent) {
        return setHttpHeader("User-Agent", userAgent);
    }

    public HttpRequest setRangeFrom(int from) {
        return setHttpHeader("Range", "bytes=" + from + "-");
    }

    public HttpRequest setRangeFromTo(int from, int to) {
        return setHttpHeader("Range", "bytes=" + from + "-" + to);
    }

    public HttpRequest setTimeout(int timeoutMillis) {
        if (connection != null)
            connection.setConnectTimeout(timeoutMillis);

        return this;
    }

    public HttpRequest setPostData(byte[] data) {
        if (connection != null) {
            connection.setDoOutput(true);

            OutputStream outputStream = null;

            try {
                outputStream = connection.getOutputStream();
                outputStream.write(data);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return this;
    }

    public HttpRequest addPostParameter(String key, String value) {
        postParams.put(key, value);
        return this;
    }

    public HttpRequest setFollowRedirectsFlag(boolean followRedirectsFlag) {
        if (connection != null) {
            connection.setInstanceFollowRedirects(followRedirectsFlag);
            HttpURLConnection.setFollowRedirects(followRedirectsFlag);
        }

        return this;
    }

    public HttpResponse send() {
        if (connection == null)
            return null;

        if (postParams.size() > 0) {
            byte[] postData = getPostDataFromParameters("UTF-8");

            if (postData != null)
                setPostData(postData);
        }

        try {
            connection.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpResponse response = new HttpResponse(connection);
        cancel();

        return response;
    }

    public void sendAsynchronously(final HttpResponseHandler handler) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpResponse response = HttpRequest.this.send();

                if (handler != null)
                    handler.onResponseDidReceive(response);
            }
        });

        thread.start();
    }

    public void cancel() {
        if (connection != null) {
            try {
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void init(URL url) {
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setUseCaches(false);

            setFollowRedirectsFlag(true);
        } catch (Exception e) {
            e.printStackTrace();
            connection = null;
        }
    }

    private byte[] getPostDataFromParameters(String charset) {
        StringBuilder postData = new StringBuilder();

        for (Map.Entry<String,String> param : postParams.entrySet()) {
            String key;
            String value;

            try {
                key = URLEncoder.encode(param.getKey(), charset);
                value = URLEncoder.encode(param.getValue(), charset);
            } catch (Exception e) {
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
            postDataBytes = postData.toString().getBytes(charset);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return postDataBytes;
    }
}


