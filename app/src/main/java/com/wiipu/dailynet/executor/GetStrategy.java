package com.wiipu.dailynet.executor;

import android.os.Handler;
import android.os.Looper;

import com.wiipu.dailynet.base.Request;
import com.wiipu.dailynet.base.Response;
import com.wiipu.dailynet.callback.AbsCallback;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author wulinpeng
 * @datetime: 17/4/10 下午3:03
 * @description:
 */
public class GetStrategy implements MethodStrategy {
    @Override
    public void deal(Request request) {
        final AbsCallback callback = request.getCallback();
        try {
            URL url = new URL(request.getUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 假设模拟
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
}