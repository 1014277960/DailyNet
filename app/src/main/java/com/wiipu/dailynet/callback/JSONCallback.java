package com.wiipu.dailynet.callback;

import com.wiipu.dailynet.base.Call;
import com.wiipu.dailynet.base.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author wulinpeng
 * @datetime: 17/5/6 下午3:45
 * @description:
 */
public abstract class JSONCallback extends Callback<JSONObject> {

    @Override
    public JSONObject parseResponse(Response response) {
        String json = response.getResult();
        JSONObject object = null;
        try {
            object = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}
