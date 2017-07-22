package com.wiipu.dailynet.executor;

import android.util.Log;

import com.wiipu.dailynet.base.Request;

import java.util.concurrent.BlockingQueue;

/**
 * @author wulinpeng
 * @datetime: 17/7/22 下午12:30
 * @description:
 */
public class NormalExecutor extends Executor {

    public NormalExecutor(BlockingQueue<Request> queue) {
        super(queue);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Request request = null;
                while (true) {
                    request = queue.take();
                    if (!request.isAttach()) {
                        break;
                    } else {
                        queue.add(request);
                        continue;
                    }
                }
                dealRequest(request);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
