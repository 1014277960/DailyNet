package com.wiipu.dailynet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.wiipu.dailynet.callback.StringCallback;
import com.wiipu.dailynet.core.DailyNet;
import com.wiipu.dailynet.core.RequestManager;

public class MainActivity extends AppCompatActivity {

    RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        final Request request = new Request.Builder()
//                .url("http://api.zhuishushenqi.com/book/fuzzy-search")
//                .method(Request.Method.GET)
//                .build();
//        RequestParam param = new RequestParam();
//        param.addParam("query", "1");
//        param.addParam("start", 0);
//        param.addParam("limit", 100);
//        request.setParam(param);
//        Call call = DailyNet.getInstance().createCall(this, request);
//        AbsCallback<String> callback = new AbsCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                Log.d("Debug", result);
//            }
//
//            @Override
//            public void onError(String msg) {
//
//            }
//
//            @Override
//            public String parseResponse(Response response) {
//                return response.getResult();
//            }
//        };
//        call.enqueue(callback);

        TestApi testApi = DailyNet.getInstance().create(TestApi.class, this);
        testApi.getNum("a704d5df2ca880f932dbaa0693e638b6", "浙江", "温州", "鹿城区").enqueue(new StringCallback() {
            @Override
            public void onSuccess(String result) {
                Log.d("Debug", result);
            }

            @Override
            public void onError(String msg) {
                Log.d("Debug", msg);
            }
        });
//        testApi.getBaidu("testPath", 2).enqueue(new StringCallback() {
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

    }
}
