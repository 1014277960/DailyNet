package com.wiipu.dailynet.core;

import android.content.Context;

import com.wiipu.dailynet.annotation.GET;
import com.wiipu.dailynet.base.Call;
import com.wiipu.dailynet.base.Request;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

/**
 * @author wulinpeng
 * @datetime: 17/4/10 下午2:33
 * @description: 提供用户访问的统一接口
 */
public class DailyNet {

    private DailyNet() {}

    public static DailyNet getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    public <T> T create(Class<T> service, final Context context) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Request request = parseMethod(method, args);
                        return createCall(context, request);
                    }
                });
    }

    /**
     * 返回动态代理对象，调用方法时解析注解，生成Request，然后调用createCall生成call返回
     * @param method
     * @param args
     * @return
     */
    private Request parseMethod(Method method, Object[] args) {
        Request.Builder builder = new Request.Builder();
        Annotation[] annotations;
        annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof GET) {
                builder.method(Request.Method.GET);
                builder.url(((GET) annotation).value());
            }
        }
        return builder.build();
    }

    /**
     * 通过context获得RequestManger，将之和Request组合，返回call
     * @param context
     * @param request
     * @return
     */
    public Call createCall(Context context, Request request) {
        Call call = new Call();
        call.setManager(RequestManager.get(context));
        call.setRequest(request);
        return call;
    }

    private enum Singleton {
        INSTANCE;
        private DailyNet dailyNet;

        private Singleton() {
            dailyNet = new DailyNet();
        }

        public DailyNet getInstance() {
            return dailyNet;
        }
    }
}
