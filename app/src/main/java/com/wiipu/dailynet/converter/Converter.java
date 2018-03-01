package com.wiipu.dailynet.converter;

/**
 * @author wulinpeng
 * @datetime: 18/3/1 下午9:34
 * @description: 转换器接口定义
 */
public interface Converter<F, T> {

    T convert(F fromValue);
}
