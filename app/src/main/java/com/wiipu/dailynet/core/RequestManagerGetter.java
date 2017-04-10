package com.wiipu.dailynet.core;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * @author wulinpeng
 * @datetime: 17/4/10 下午3:49
 * @description: 返回与context生命周期绑定的RequestManager
 */
public class RequestManagerGetter {

    private static final String FRAGMENT_TAG = "fragment";

    /**
     * 全局的RequestManger，单例
     */
    private static volatile RequestManager applicationManager;

    public static RequestManagerGetter getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    public RequestManager get(Context context) {
        if (context == null) {
            throw new NullPointerException();
        } else if (!(context instanceof Application)) {
            if (context instanceof FragmentActivity) {
                return get((FragmentActivity) context);
            } else if (context instanceof Activity) {
                return get((Activity) context);
            }
        }
        // context为application
        return getApplicationManager(context);
    }

    public RequestManager get(FragmentActivity activity) {
        FragmentManager fm = activity.getSupportFragmentManager();
        return supportFragmentGet(activity, fm);
    }

    public RequestManager get(Activity activity) {
        android.app.FragmentManager fm = activity.getFragmentManager();
        return fragmentGet(activity, fm);
    }

    /**
     * 全局RequestManager,生命周期为整个application，只有一个
     * @param context
     * @return
     */
    public RequestManager getApplicationManager(Context context) {
        if (applicationManager == null) {
            synchronized (this) {
                if (applicationManager == null) {
                    applicationManager = new RequestManager();
                    applicationManager.onStart();
                }
            }
        }
        return applicationManager;
    }

    public RequestManager supportFragmentGet(FragmentActivity activity, FragmentManager fm) {
        SupportManagerFragment fragment = (SupportManagerFragment) fm.findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null) {
            fragment = new SupportManagerFragment();
            fm.beginTransaction().add(fragment, FRAGMENT_TAG).commit();
        }
        RequestManager requestManager = (RequestManager) fragment.getLifecycleListener();
        if (requestManager == null) {
            requestManager = new RequestManager();
            fragment.setLifecycleListener(requestManager);
        }
        return requestManager;
    }

    public RequestManager fragmentGet(Activity activity, android.app.FragmentManager fm) {
        ManagerFragment fragment = (ManagerFragment) fm.findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null) {
            fragment = new ManagerFragment();
            fm.beginTransaction().add(fragment, FRAGMENT_TAG).commit();
        }
        RequestManager requestManager = (RequestManager) fragment.getLifecycleListener();
        if (requestManager == null) {
            requestManager = new RequestManager();
            fragment.setLifecycleListener(requestManager);
        }
        return requestManager;
    }

    private enum Singleton {
        INSTANCE;
        private RequestManagerGetter getter;
        private Singleton() {
            getter = new RequestManagerGetter();
        }

        public RequestManagerGetter getInstance() {
            return getter;
        }
    }
}
