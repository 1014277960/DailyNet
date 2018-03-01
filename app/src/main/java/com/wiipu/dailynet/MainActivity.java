package com.wiipu.dailynet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.wiipu.dailynet.base.Call;
import com.wiipu.dailynet.base.Request;
import com.wiipu.dailynet.base.RequestParam;
import com.wiipu.dailynet.base.Response;
import com.wiipu.dailynet.callback.AutoConvertCallback;
import com.wiipu.dailynet.callback.Callback;
import com.wiipu.dailynet.callback.StringCallback;
import com.wiipu.dailynet.converter.JsonConverterFactory;
import com.wiipu.dailynet.core.DailyNet;
import com.wiipu.dailynet.core.Options;
import com.wiipu.dailynet.core.RequestManager;

public class MainActivity extends AppCompatActivity {

    RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // master test
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

        DailyNet.getInstance().setOptions(new Options.Builder().addConverterFactory(JsonConverterFactory.getInstance()).build());
        Request request = new Request.Builder()
                .url("http://192.168.0.107:8080/api/test")
                .method(Request.Method.GET)
                .build();
        Call call = DailyNet.getInstance().createCall(this, request);
        call.enqueue(new AutoConvertCallback<TestBean>() {
            @Override
            public void onSuccess(TestBean result) {
                Log.d("Debug", "11111111111111111");
                TestBean testBean = result;

            }

            @Override
            public void onError(String msg) {
                Log.d("Debug", msg);
            }
        });

//        TestApi testApi = DailyNet.getInstance().create(TestApi.class, this);
//        testApi.getNum("a704d5df2ca880f932dbaa0693e638b6", "浙江", "温州", "鹿城区").enqueue(new StringCallback() {
//            @Override
//            public void onSuccess(String result) {
//                Log.d("Debug", result);
//            }
//
//            @Override
//            public void onError(String msg) {
//                Log.d("Debug", msg);
//            }
//        });

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

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                Log.d("Debug", "wake");
//                final Request request = new Request.Builder()
//                        .url("http://api.zhuishushenqi.com/book/fuzzy-search")
//                        .method(Request.Method.GET)
//                        .build();
//                request.setAttach(false);
//                RequestParam param = new RequestParam();
//                param.addParam("query", "1");
//                param.addParam("start", 0);
//                param.addParam("limit", 100);
//                request.setParam(param);
//                Call call = DailyNet.getInstance().createCall(MainActivity.this, request);
//                Callback<String> callback = new Callback<String>() {
//                    @Override
//                    public void onSuccess(String result) {
//                        Log.d("Debug", result.substring(0, 20));
//                    }
//
//                    @Override
//                    public void onError(String msg) {
//                        Log.d("Debug", msg);
//                    }
//
//                    @Override
//                    public String parseResponse(Response response) {
//                        return response.getResult();
//                    }
//                };
//                call.enqueue(callback);
//            }
//        }).start();
//        return super.onTouchEvent(event);
//    }
}
