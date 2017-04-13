package com.wiipu.dailynet;

import com.wiipu.dailynet.annotation.GET;
import com.wiipu.dailynet.annotation.Path;
import com.wiipu.dailynet.annotation.Query;
import com.wiipu.dailynet.base.Call;

/**
 * @author wulinpeng
 * @datetime: 17/4/10 下午3:12
 * @description:
 */
public interface TestApi {

    @GET("http://www.baidu.com")
    public Call getBaidu(@Path("id") String id, @Query("q") int query);
}
