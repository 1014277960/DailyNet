package com.wiipu.dailynet.converter;

import com.wiipu.dailynet.base.Response;

/**
 * @author wulinpeng
 * @datetime: 18/3/1 下午10:20
 * @description: 由于Converter有范型，需要转换为具体的类型，所以创建Converter的时候需要传入真正的类型，由factory来做
 * todo 参考retrofit重新架构，了解type相关内容
 */
public interface ConverterFactory {

    Converter<Response, ?> create(Class<?> tClass);
}
