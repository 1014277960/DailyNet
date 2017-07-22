package com.wiipu.dailynet.executor;

import com.wiipu.dailynet.base.Request;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * @author wulinpeng
 * @datetime: 17/7/22 下午12:23
 * @description: 处理器基类，提供基本的功能，子类可以扩展关联生命周期
 */
public abstract class Executor extends Thread {

    protected BlockingQueue<Request> queue;

    /**
     * 策略模式，保存所有策略，通过method获得具体策略执行方法
     */
    protected Map<Request.Method, MethodStrategy> strategies;

    public Executor(BlockingQueue<Request> queue) {
        this.queue = queue;
        strategies = new HashMap<>();
        strategies.put(Request.Method.GET, new GetStrategy());
        strategies.put(Request.Method.POST, new PostStrategy());
    }

    protected void dealRequest(Request request) {
        Request.Method method = request.getMethod();
        strategies.get(method).deal(request);
    }

}
