package com.wiipu.dailynet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.wiipu.dailynet.callback.AbsCallback;
import com.wiipu.dailynet.base.Request;
import com.wiipu.dailynet.base.Response;
import com.wiipu.dailynet.callback.StringCallback;
import com.wiipu.dailynet.core.DailyNet;
import com.wiipu.dailynet.core.RequestManager;

public class MainActivity extends AppCompatActivity {

    RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        final Request request = new Request.Builder().url("http://www.baidu.com")
//                .method(Request.Method.GET)
//                .build();
//        DailyNet.getInstance().createCall(this, request).enqueue(new StringCallback() {
//            @Override
//            public void onSuccess(String result) {
//                Log.d("Debug", result);
//            }
//
//            @Override
//            public void onError(String msg) {
//
//            }
//        });
        TestApi testApi = DailyNet.getInstance().create(TestApi.class, this);
        testApi.getBaidu().enqueue(new StringCallback() {
            @Override
            public void onSuccess(String result) {
                Log.d("Debug", result);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        requestManager.start();
        return super.onTouchEvent(event);
    }
}
