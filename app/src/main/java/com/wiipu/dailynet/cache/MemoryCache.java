package com.wiipu.dailynet.cache;

import android.util.LruCache;

import com.wiipu.dailynet.base.Response;

/**
 * @author wulinpeng
 * @datetime: 17/5/6 下午3:58
 * @description: 单例，全局共享
 */
public class MemoryCache implements Cache<String, Response> {

    private LruCache<String, Response> lruCache;

    public static MemoryCache getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    private MemoryCache() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        lruCache = new LruCache<String, Response>(maxMemory) {
            @Override
            protected int sizeOf(String key, Response value) {
                return value.getResult().getBytes().length / 1024;
            }
        };
    }

    @Override
    public Response get(String key) {
        return lruCache.get(key);
    }

    @Override
    public void put(String key, Response response) {
        lruCache.put(key, response);
    }

    @Override
    public void remove(String key) {
        lruCache.remove(key);
    }

    @Override
    public void clear() {
        lruCache.evictAll();
    }

    private enum Singleton {
        INSTANCE;
        private MemoryCache instance;

        private Singleton() {
            instance = new MemoryCache();
        }

        public MemoryCache getInstance() {
            return instance;
        }
    }
}
