package com.wiipu.dailynet.core;

import android.content.Context;

import com.wiipu.dailynet.annotation.GET;
import com.wiipu.dailynet.annotation.POST;
import com.wiipu.dailynet.annotation.Path;
import com.wiipu.dailynet.annotation.Query;
import com.wiipu.dailynet.base.Call;
import com.wiipu.dailynet.base.Request;
import com.wiipu.dailynet.base.RequestParam;

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

    /**
     * 返回动态代理对象，调用方法时解析注解，生成Request，然后调用createCall生成call返回
     * @param service
     * @param context
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service, final Context context) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // 判断是否是Object的方法
                        if (method.getDeclaringClass() == Object.class) {
                            return method.invoke(this, args);
                        }
                        Request request = parseMethod(method, args);
                        return createCall(context, request);
                    }
                });
    }

    private Request parseMethod(Method method, Object[] args) {
        Request request = null;
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            // 解析GET或POST请求
            if (annotation instanceof GET || annotation instanceof POST) {
                request = buildRequest(method, args, annotation);
                break;
            }
        }
        if (request == null) {
            throw new RuntimeException("Interface annotation error!");
        }
        return request;
    }

    /**
     * 根据注解与参数构造Request
     * @param method
     * @param args
     * @param methodAnnotation
     * @return
     */
    private Request buildRequest(Method method, Object[] args, Annotation methodAnnotation) {
        Request.Builder builder = new Request.Builder();
        String url = "";
        if (methodAnnotation instanceof GET) {
            builder.method(Request.Method.GET);
            url = ((GET) methodAnnotation).value();
        } else if (methodAnnotation instanceof POST) {
            builder.method(Request.Method.POST);
            url = ((POST) methodAnnotation).value();
        }
        Type[] types = method.getGenericParameterTypes();
        Annotation[][] paramAnnotations = method.getParameterAnnotations();
        RequestParam param = new RequestParam();

        // 解析参数
        for (int i = 0; i != paramAnnotations.length; ++i) {
            Annotation[] paramAnnotation = paramAnnotations[i];
            if (paramAnnotation.length == 0) {
                continue;
            }
            Annotation a = paramAnnotation[0];
            // 替换url中的{path}
            if (a instanceof Path) {
                String key = ((Path) a).value();
                url = url.replaceFirst("\\{" + key + "\\}", String.valueOf(args[i]));
            } else if (a instanceof Query) {
                // 添加参数，不直接添加到url后面，作为成员变量传入，转换url交给GetStrategy来做
                String key = ((Query) a).value();
                Object value = args[i];
                if (value instanceof Integer || value instanceof String) {
                    param.addParam(key, String.valueOf(value));
                } else {
                    throw new RuntimeException("HTTP param type error!");
                }
            }

        }
        builder.url(url).param(param);
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
