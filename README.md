# DailyNet
简单易用的Android网络请求框架，自动绑定生命周期调度请求。
# 使用方法
```
    // 第一种方式请求GET
    final Request request = new Request.Builder()
            .url("http://www.baidu.com")
            .method(Request.Method.GET)
            .build();
    Call call = DailyNet.getInstance().createCall(this, request);
    AbsCallback<String> callback = new AbsCallback<String>() {
        @Override
        public void onSuccess(String result) {
            Log.d("Debug", result);
        }

        @Override
        public void onError(String msg) {}

        @Override
        public String parseResponse(Response response) {
            return response.getResult();
        }
    };
    call.enqueue(callback);
        
    // 第二种方式，首先编写接口文件TestApi
    public interface TestApi {

        @GET("http://www.baidu.com")
        public Call getBaidu();
    }
    
    // 请求代码
    TestApi testApi = DailyNet.getInstance().create(TestApi.class, this);
    testApi.getBaidu().enqueue(new StringCallback() {
        @Override
        public void onSuccess(String result) {
            Log.d("Debug", result);
        }

        @Override
        public void onError(String msg) {}
    });

```
# 已完成功能
 - 基本的Get请求
 - 基本的Post请求
 - 自动绑定FragmentActivity、Activity、ApplicationContext的生命周期
