package com.coco.inspirelink.unknowtest;

/**
 * @Author: MOHE
 * @Description: TODO
 * @Date: 2025/5/19 下午5:00
 * @Version: 1.0
 */

public class a {
    public static void main(String args[]) {
        GenericMethod d = new GenericMethod(); // 创建 GenericMethod 对象

        String str = d.fun("汤姆"); // 给GenericMethod中的泛型方法传递字符串
        int i = d.fun(30);  // 给GenericMethod中的泛型方法传递数字，自动装箱
        System.out.println(str); // 输出 汤姆
        System.out.println(i);  // 输出 30

        GenericMethod.show("Lin");// 输出: 静态泛型方法 Lin
    }
}

class GenericMethod {
    // 普通的泛型方法
    public <T> T fun(T t) { // 可以接收任意类型的数据
        return t;
    }

    // 静态的泛型方法
    public static <E> void show(E one){
        System.out.println("静态泛型方法 " + one);
    }
}
