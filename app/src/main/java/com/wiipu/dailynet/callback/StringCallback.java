package com.wiipu.dailynet.callback;

import com.wiipu.dailynet.base.Response;

/**
 * @author wulinpeng
 * @datetime: 17/4/6 下午11:18
 * @description:
 */
public abstract class StringCallback extends Callback<String> {

    @Override
    public String parseResponse(Response response) {
        return response.getResult();
    }
}
