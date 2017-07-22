package com.wiipu.dailynet.core;

import android.content.Context;
import android.util.Log;

import com.wiipu.dailynet.base.Request;
import com.wiipu.dailynet.executor.AttachedExecutor;
import com.wiipu.dailynet.executor.NormalExecutor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author wulinpeng
 * @datetime: 17/4/6 下午9:44
 * @description: 管理请求，与context生命周期绑定
 */
public class RequestManager implements LifecycleListener {

    private static final int MAX_SIZE = 10;

    private BlockingQueue<Request> requestQueue;

    private AttachedExecutor[] attachedExecutors;

    private NormalExecutor[] normalExecutors;

    public static RequestManager get(Context context) {
        // 通过RequestManagerGetter得到和context生命周期绑定的RequestManager
        return RequestManagerGetter.getInstance().get(context);
    }

    protected RequestManager() {
        requestQueue = new LinkedBlockingQueue<>();
        attachedExecutors = new AttachedExecutor[MAX_SIZE];
        normalExecutors = new NormalExecutor[MAX_SIZE];
        for (int i = 0; i != MAX_SIZE; ++i) {
            attachedExecutors[i] = new AttachedExecutor(requestQueue);
            normalExecutors[i] = new NormalExecutor(requestQueue);
            attachedExecutors[i].start();
            normalExecutors[i].start();
        }
    }

    public void enqueue(Request request) {
        requestQueue.add(request);
    }

    private void start() {
        for (int i = 0; i != MAX_SIZE; ++i) {
            if (attachedExecutors[i].isPause()) {
                // 此时状态时pause
                attachedExecutors[i].setPause(false);
            } else {
                // 说明此时要么是刚开始的时候没有start，要么是被stop了，都重新开始
                // 注意一个thread不能多次start，所以new新的
                attachedExecutors[i] = new AttachedExecutor(requestQueue);
                attachedExecutors[i].start();
            }

        }
    }

    public void pause() {
        for (int i = 0; i != MAX_SIZE; ++i) {
            if (attachedExecutors[i] != null) {
                attachedExecutors[i].setPause(true);
            }
        }
    }

    public void stop() {
        for (int i = 0; i != MAX_SIZE; ++i) {
            if (attachedExecutors[i] != null) {
                attachedExecutors[i].setStop();
            }
        }
    }

    @Override
    public void onStart() {
        Log.d("Debug", "onStart");
        start();
    }

    @Override
    public void onPause() {
        Log.d("Debug", "onPause");
        pause();
    }

    @Override
    public void onStop() {
        Log.d("Debug", "onStop");
        stop();
    }
}
