package com.wiipu.dailynet.base;

import com.wiipu.dailynet.callback.Callback;
import com.wiipu.dailynet.core.RequestManager;

/**
 * @author wulinpeng
 * @datetime: 17/4/10 下午2:28
 * @description: 用户最终得到的类，组合request和manager，调用enqueue完成任务插入
 */
public class Call {
    private Request request;
    private RequestManager manager;

    public void enqueue(Callback<?> callback)  {
        if (manager != null && request != null) {
            request.setCallback(callback);
            manager.enqueue(request);
        }
    }


    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public RequestManager getManager() {
        return manager;
    }

    public void setManager(RequestManager manager) {
        this.manager = manager;
    }
}
