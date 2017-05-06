package com.wiipu.dailynet.executor;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.wiipu.dailynet.base.Request;
import com.wiipu.dailynet.base.Response;
import com.wiipu.dailynet.cache.MemoryCache;
import com.wiipu.dailynet.callback.Callback;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * @author wulinpeng
 * @datetime: 17/4/10 下午3:03
 * @description: 只在GET里有缓存策略
 */
public class GetStrategy implements MethodStrategy {
    @Override
    public void deal(Request request) {
        final Callback callback = request.getCallback();

        // 读取缓存
        if (loadFromCache(request)) {
            return;
        }

        try {
            String urlString = getStringUrl(request);
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == 200) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                Response response = new Response();
                response.setSuccess(true);
                response.setResult(sb.toString());
                // 缓存结果
                cacheResponse(urlString, response);
                if (callback != null && !request.isCancel()) {
                    final Object o = callback.parseResponse(response);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(o);
                        }
                    });
                }
            } else {
                if (callback != null && !request.isCancel()) {
                    callback.onError("连接失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (callback != null) {
                callback.onError(e.getMessage());
            }
        }
    }

    private boolean loadFromCache(Request request) {
        String url = getStringUrl(request);
        Response response = MemoryCache.getInstance().get(url);
        if (response == null) {
            return false;
        }
        if (request.getCallback() != null) {
            request.getCallback().onSuccess(request.getCallback().parseResponse(response));
        }
        return true;
    }

    private void cacheResponse(String key, Response response) {
        MemoryCache.getInstance().put(key, response);
    }

    /**
     * 由于HashMap的keySet是会自动排序的，所以不必担心get请求相同参数不同顺序的情况，最后只会有一样的url，保证缓存key一样
     * @param request
     * @return
     */
    private String getStringUrl(Request request) {
        StringBuilder sb = new StringBuilder(request.getUrl());
        if (request.getParam() != null) {
            char ch = '?';
            if (request.getUrl().matches(".+?\\?.+?=.+?")) {
                ch = '&';
            }
            Map<String, String> map = request.getParam().getParams();
            for (String key : map.keySet()) {
                sb.append(ch);
                if (ch == '?') {
                    ch = '&';
                }
                sb.append(key + "=" + map.get(key));
            }
        }
        return sb.toString();
    }
}
