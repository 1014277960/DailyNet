package com.wiipu.dailynet.executor;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.wiipu.dailynet.base.Request;
import com.wiipu.dailynet.base.RequestParam;
import com.wiipu.dailynet.base.Response;
import com.wiipu.dailynet.callback.AbsCallback;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * @author wulinpeng
 * @datetime: 17/4/15 下午3:07
 * @description:
 */
public class PostStrategy implements MethodStrategy {
    @Override
    public void deal(Request request) {
        final AbsCallback callback = request.getCallback();
        try {
            URL url = new URL(request.getUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            String param = getParam(request);
            PrintWriter writer=new PrintWriter(connection.getOutputStream());
            writer.write(param);
            writer.flush();
            writer.close();
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

    private String getParam(Request request) {
        RequestParam param = request.getParam();
        if (param == null || param.getParams() == null || param.getParams().size() <= 0) {
            return "";
        }
        Map<String, String> map = param.getParams();
        StringBuilder sb = new StringBuilder();
        String s = "";
        for (String key : map.keySet()) {
            String value = map.get(key);
            sb.append(s);
            sb.append(key + "=" + value);
            if (s == "") {
                s = "&";
            }
        }
        return sb.toString();
    }
}
