package com.wiipu.dailynet.converter;

/**
 * @author wulinpeng
 * @datetime: 18/3/1 下午9:57
 * @description:
 */
public class JsonConverterFactory implements ConverterFactory {

    public static JsonConverterFactory instance = new JsonConverterFactory();

    private JsonConverterFactory() {
    }

    public static JsonConverterFactory getInstance() {
        return instance;
    }

    public JsonConverter create(Class<?> clazz) {
        return new JsonConverter<>(clazz);
    }
}
