package com.doubao.douding.debug;

import org.json.JSONObject;

/**
 * @author zhangs890
 * @Description show debug
 */
public class DebugBreakPoint {

    public static void line() {
        System.out.println("行断点");
    }

    public static void method() {
        IService service = new IServiceImpl();
        service.doSth();
    }

    public static void makeException() {
        JSONObject obj =null;
        System.out.println(obj.toString());
    }

    public static void field() {
        Animal animal = new Animal("Cat", 1);
        animal.setAge(2);
        System.out.println(animal);
    }

    public static void forceResturn() {
        System.out.println("1");
        System.out.println("2");
        System.out.println("3");
        System.out.println("4");
        System.out.println("5");
    }

    public static void main(String[] args) {
//        line();

//        method();

//        makeException();
        field();

    }
}
