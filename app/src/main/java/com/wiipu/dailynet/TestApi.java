package com.wiipu.dailynet;

import com.wiipu.dailynet.annotation.GET;
import com.wiipu.dailynet.annotation.POST;
import com.wiipu.dailynet.annotation.Path;
import com.wiipu.dailynet.annotation.Query;
import com.wiipu.dailynet.base.Call;

/**
 * @author wulinpeng
 * @datetime: 17/4/10 下午3:12
 * @description:
 */
public interface TestApi {

    @GET("http://www.baidu.com/{path}?a=1")
    public Call getBaidu(@Path("path") String id, @Query("q") int query);

    @POST("http://apis.juhe.cn/train/dsd")
    public Call getNum(@Query("key") String key, @Query("province") String province,
                       @Query("city") String city, @Query("county") String county);
}
