package com.gzhc365.vo;

/**
 * @Author: ZhouYuling
 * @Description:
 * @Date: Created in 下午 12:33 2019/7/17 0017
 * @Modified by:
 */
public class Student {

    private String name;
    private int age;

    public Student() {
    }

    public Student(int age, String name) {
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
