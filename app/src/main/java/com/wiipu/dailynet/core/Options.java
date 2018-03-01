package com.wiipu.dailynet.core;

import com.wiipu.dailynet.converter.ConverterFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @author wulinpeng
 * @datetime: 18/3/1 下午10:25
 * @description: 配置
 */
public class Options {

    private Options() {
    }

    private Set<ConverterFactory> mConverterFactorySet;

    public Set<ConverterFactory> getConverterFactorySet() {
        return mConverterFactorySet;
    }

    public static class Builder {
        private Set<ConverterFactory> mConverterFactorySet;

        public Options build() {
            Options options = new Options();
            options.mConverterFactorySet = mConverterFactorySet;

            return options;
        }

        public Builder addConverterFactory(ConverterFactory factory) {
            if (mConverterFactorySet == null) {
                mConverterFactorySet = new HashSet<>();
            }
            mConverterFactorySet.add(factory);
            return this;
        }
    }
}
