package com.doubao.douding.debug;

/**
 * @author zhangs890
 * @Description TODO
 */
public class Animal {
    private String name;
    private Integer age;

    public Animal() {
    }

    public Animal(final String name, final Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }
}
