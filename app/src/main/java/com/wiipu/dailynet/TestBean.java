package com.wiipu.dailynet;

/**
 * @author wulinpeng
 * @datetime: 18/3/1 下午10:13
 * @description:
 */
public class TestBean {

    private String name;
    private int age;

    public TestBean(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
