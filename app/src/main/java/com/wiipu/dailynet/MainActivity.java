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
}
