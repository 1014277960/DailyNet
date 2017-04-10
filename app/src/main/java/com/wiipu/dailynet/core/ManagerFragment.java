package com.wiipu.dailynet.core;

import android.app.Fragment;
import android.app.FragmentManager;

/**
 * @author wulinpeng
 * @datetime: 17/4/10 下午4:21
 * @description:
 */
public class ManagerFragment extends Fragment {

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
