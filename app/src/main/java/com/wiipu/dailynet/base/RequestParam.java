package com.wiipu.dailynet.base;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wulinpeng
 * @datetime: 17/4/6 下午9:34
 * @description:
 */
public class RequestParam {
    private Map<String, String> params;

    public RequestParam() {
        params = new HashMap<>();
    }

    public void addParam(String key, String value) {
        params.put(key, value);
    }
}
