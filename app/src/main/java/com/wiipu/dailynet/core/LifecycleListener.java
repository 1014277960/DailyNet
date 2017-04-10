package com.wiipu.dailynet.core;

/**
 * @author wulinpeng
 * @datetime: 17/4/10 下午4:06
 * @description: 生命周期回调接口
 */
public interface LifecycleListener {

    public void onStart();

    public void onPause();

    public void onStop();
}
