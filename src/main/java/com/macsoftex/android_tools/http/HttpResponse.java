package com.macsoftex.android_tools.http;

import com.macsoftex.android_tools.FileTools;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by alex-v on 15.09.17.
 */

public class HttpResponse {
    private byte[] data;
    private Map<String, List<String>> headers;
    private int resposeCode, contentLength;

    public HttpResponse(HttpURLConnection connection) {
        try {
            data = FileTools.getBytesFromInputStream(connection.getInputStream());
            headers = connection.getHeaderFields();
            contentLength = connection.getContentLength();
            resposeCode = connection.getResponseCode();
        } catch (Exception | OutOfMemoryError e) {
            e.printStackTrace();
            resetData();
        }
    }

    public boolean isError() {
        return data == null || headers == null;
    }

    public byte[] getData() {
        if (isError())
            return null;

        return data;
    }

    public String getText() {
        if (isError())
            return null;

        return new String(data);
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public int getContentLength() {
        return contentLength;
    }

    public int getResposeCode() {
        return resposeCode;
    }

    private void resetData () {
        data = null;
        headers = null;
        contentLength = 0;
        resposeCode = 0;
    }
}
