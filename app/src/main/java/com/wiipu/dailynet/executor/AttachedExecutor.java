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
                // todo 取消暂停功能，无法实现，而且死循环这方法太垃圾
                while (isPause) {
                    // 暂停死循环，直到结束
                }
                if (!request.isCancel()) {
                    dealRequest(request);

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                // stop的时候退出运行,且request自动丢弃
                if (isStop) {
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
