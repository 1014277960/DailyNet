package com.wiipu.dailynet.callback;

import com.wiipu.dailynet.base.Response;

/**
 * @author wulinpeng
 * @datetime: 17/4/6 下午9:26
 * @description:
 */
public abstract class Callback<T> {
    public abstract void onSuccess(T result);
    public abstract void onError(String msg);

    public abstract T parseResponse(Response response);
}
