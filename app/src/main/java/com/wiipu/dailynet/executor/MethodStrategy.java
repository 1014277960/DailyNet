package com.wiipu.dailynet.executor;

import com.wiipu.dailynet.base.Request;

/**
 * @author wulinpeng
 * @datetime: 17/4/10 下午3:03
 * @description:
 */
public interface MethodStrategy {

    public void deal(Request request);
}
