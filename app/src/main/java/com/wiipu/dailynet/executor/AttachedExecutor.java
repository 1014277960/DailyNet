package com.wiipu.dailynet.executor;

import android.util.Log;

import com.wiipu.dailynet.R;
import com.wiipu.dailynet.base.Request;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * @author wulinpeng
 * @datetime: 17/4/6 下午10:55
 * @description: 实际执行request的类,关联生命周期
 */
public class AttachedExecutor extends Executor {

    private volatile boolean isPause = false;

    private volatile boolean isStop = false;

    public AttachedExecutor(BlockingQueue<Request> queue) {
        super(queue);
    }

    @Override
    public void run() {
        isStop = false;
        if (queue == null) {
            throw new RuntimeException("RequestQueue can not be null!");
        }

        Request request = null;
        while (true) {
            try {
                // 判断拿到的请求是否支持绑定生命周期
                while (true) {
                    request = queue.take();
                    if (request.isAttach()) {
                        Log.d("Debug", "attach: is attach");
                        break;
                    } else {
                        Log.d("Debug", "attach: not attach");
                        queue.add(request);
                        continue;
                    }
                }
                while (isPause) {
                    // 暂停死循环，直到结束
                }
                if (!request.isCancel()) {
                    dealRequest(request);

                }
            } catch (InterruptedException e) {
                // TODO: 17/7/22 修复stop后还是存在会丢掉request的情况
                e.printStackTrace();
                // stop的时候退出运行
                if (isStop) {
                    // 中断后把request放回原处
                    if (request != null) {
                        queue.add(request);
                        request = null;
                    }
                    return;
                }
                continue;
            }
        }
    }

    public void setPause(boolean pause) {
        isPause = pause;
    }

    public void setStop() {
        isStop = true;
        interrupt();
    }

    public boolean isPause() {
        return isPause;
    }

    public boolean isStop() {
        return isStop;
    }
}
