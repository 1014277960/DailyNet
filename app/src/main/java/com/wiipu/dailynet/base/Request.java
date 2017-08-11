package com.wiipu.dailynet.base;

import android.os.Bundle;

import com.wiipu.dailynet.callback.Callback;

/**
 * @author wulinpeng
 * @datetime: 17/4/6 下午9:30
 * @description: 只是存储请求信息的一个实体类，没有执行的操作,线程池最终只接受这个类，回调其中的callback
 */
public class Request {

    public enum Method {
        GET,
        POST
    }

    private Method method;
    private String url;
    private RequestParam param;
    boolean isCancel;
    boolean isAttach = true;

    private Callback callback;

    private Request(Builder builder) {
        this.method = builder.method;
        this.url = builder.url;
        this.param = builder.param;
        this.isAttach = builder.isAttach;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RequestParam getParam() {
        return param;
    }

    public void setParam(RequestParam param) {
        this.param = param;
    }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public boolean isCancel() {
        return isCancel;
    }

    public void setCancel(boolean cancel) {
        isCancel = cancel;
    }

    public boolean isAttach() {
        return isAttach;
    }

    public void setAttach(boolean attach) {
        isAttach = attach;
    }

    public static class Builder {
        private Method method = Method.GET;
        private String url = "";
        private RequestParam param;
        private boolean isAttach = false;

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder method(Method method) {
            this.method = method;
            return this;
        }

        public Builder param(RequestParam param) {
            this.param = param;
            return this;
        }

        public Builder attach(boolean attach) {
            this.isAttach = attach;
            return this;
        }

        public Request build() {
            return new Request(this);
        }
    }
}
