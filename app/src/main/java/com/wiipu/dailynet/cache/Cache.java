package com.wiipu.dailynet.cache;

import com.wiipu.dailynet.base.Request;
import com.wiipu.dailynet.base.Response;

/**
 * @author wulinpeng
 * @datetime: 17/5/6 下午3:56
 * @description: 设计为范型，保证可扩展性
 */
public interface Cache<K, V> {

    public V get(K key);

    public void put(K key, V value);

    public void remove(K key);

    public void clear();
}
