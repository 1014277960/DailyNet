package com.wiipu.dailynet.callback;

import com.wiipu.dailynet.base.Response;
import com.wiipu.dailynet.converter.Converter;
import com.wiipu.dailynet.converter.ConverterFactory;
import com.wiipu.dailynet.converter.JsonConverter;
import com.wiipu.dailynet.converter.JsonConverterFactory;
import com.wiipu.dailynet.core.DailyNet;

import java.lang.reflect.ParameterizedType;
import java.util.Set;

/**
 * @author wulinpeng
 * @datetime: 18/3/1 下午9:28
 * @description: 自动转换类型，通过查找配置的转换器{Conver}
 */
public abstract class AutoConvertCallback<T> extends Callback<T> {
    @Override
    public T parseResponse(Response response) {
        Set<ConverterFactory> factories = DailyNet.getInstance().getOptions().getConverterFactorySet();
        if (factories == null || factories.size() <= 0) {
            return null;
        }

        for (ConverterFactory factory : factories) {
            Converter<String, T> converter = factory.create(getGenericClass());
            String json = response.getResult();
            T result = converter.convert(json);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private Class<T> getGenericClass() {
        return (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
