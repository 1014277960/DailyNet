package com.wiipu.dailynet.executor;

import com.wiipu.dailynet.base.Request;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * @author wulinpeng
 * @datetime: 17/4/6 下午10:55
 * @description: 实际执行request的类
 */
public class Executor extends Thread {

    private volatile boolean isPause = false;

    private BlockingQueue<Request> queue;

    /**
     * 策略模式，保存所有策略，通过method获得具体策略执行方法
     */
    private Map<Request.Method, MethodStrategy> strategies;

    public Executor(BlockingQueue<Request> queue) {
        this.queue = queue;
        strategies = new HashMap<>();
        strategies.put(Request.Method.GET, new GetStrategy());
        strategies.put(Request.Method.POST, new PostStrategy());
    }

    @Override
    public void run() {
        // 判断是否该停止，如果被停止了，结束方法，同时恢复标志位(isInterrupt不回复标志位)
        if (queue == null) {
            throw new RuntimeException("RequestQueue can not be null!");
        }
        while (true) {
            try {
                Request request = queue.take();
                while (isPause) {
                    // 暂停死循环，直到结束
                }
                if (!request.isCancel()) {
                    dealRequest(request);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                // stop的时候推出运行
                return;
            }
        }
    }

    private void dealRequest(Request request) {
        Request.Method method = request.getMethod();
        strategies.get(method).deal(request);
    }

    public void setPause(boolean pause) {
        isPause = pause;
    }

    public void setStop(boolean stop) {
        interrupt();
    }
}
