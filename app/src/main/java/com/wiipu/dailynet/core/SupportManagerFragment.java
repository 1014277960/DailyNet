package com.wiipu.dailynet.core;

import android.support.v4.app.Fragment;

/**
 * @author wulinpeng
 * @datetime: 17/4/10 下午4:04
 * @description: 与FragmentActivity绑定的Fragment，用于监听与FragmentActivity绑定的Fragment的生命周期，以便回调lifecycleListener
 */
public class SupportManagerFragment extends Fragment {

    private LifecycleListener lifecycleListener;

    public void setLifecycleListener(LifecycleListener listener) {
        lifecycleListener = listener;
    }

    public LifecycleListener getLifecycleListener() {
        return lifecycleListener;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (lifecycleListener != null) {
            lifecycleListener.onStart();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (lifecycleListener != null) {
            lifecycleListener.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (lifecycleListener != null) {
            lifecycleListener.onStop();
        }
    }
}
