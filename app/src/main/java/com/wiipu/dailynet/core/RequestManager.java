package com.wiipu.dailynet.core;

import android.content.Context;

import com.wiipu.dailynet.base.Request;
import com.wiipu.dailynet.executor.Executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author wulinpeng
 * @datetime: 17/4/6 下午9:44
 * @description: 管理请求，与context生命周期绑定
 */
public class RequestManager {

    private static final int MAX_SIZE = 10;

    private BlockingQueue<Request> requestQueue;

    private Executor[] executors;

    public static RequestManager get(Context context) {
        // TODO: 17/4/6 返回绑定生命周期的RequestManager
        return new RequestManager();
    }

    private RequestManager() {
        requestQueue = new LinkedBlockingQueue<>();
        executors = new Executor[MAX_SIZE];
        for (int i = 0; i != MAX_SIZE; ++i) {
            executors[i] = new Executor(requestQueue);
            executors[i].start();
        }
    }

    public void enqueue(Request request) {
        requestQueue.add(request);
    }

    public void start() {
        for (int i = 0; i != MAX_SIZE; ++i) {
            if (!executors[i].isAlive()) {
                executors[i] = new Executor(requestQueue);
                executors[i].start();
            } else {
                executors[i].setPause(false);
            }

        }
    }

    public void pause() {
        for (int i = 0; i != MAX_SIZE; ++i) {
            if (executors[i] != null) {
                executors[i].setPause(true);
            }
        }
    }

    public void stop() {
        for (int i = 0; i != MAX_SIZE; ++i) {
            if (executors[i] != null) {
                executors[i].setStop(true);
            }
        }
    }
}
