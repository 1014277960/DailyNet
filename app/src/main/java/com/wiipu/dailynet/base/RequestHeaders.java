package com.wiipu.dailynet.base;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wulinpeng on 2017/8/11.
 */

public class RequestHeaders {

    private Map<String, String> headers;

    public RequestHeaders() {
        headers = new HashMap<>();
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        if (headers != null) {
            this.headers = headers;
        }
    }
}
