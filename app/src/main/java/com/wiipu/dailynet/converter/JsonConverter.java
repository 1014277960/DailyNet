package com.wiipu.dailynet.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.lang.reflect.Type;
import java.util.Objects;

/**
 * @author wulinpeng
 * @datetime: 18/3/1 下午9:35
 * @description: 将json数据转换为object
 */
public class JsonConverter<T> implements Converter<String, T> {

    private Class tClass;

    JsonConverter(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public T convert(String fromValue) {
        Gson gson = new Gson();
        T result  = (T) gson.fromJson(fromValue, tClass);
        return result;
    }
}
