package com.foolxing.mall.util;

@SuppressWarnings("all")
<<<<<<< HEAD
=======

>>>>>>> 2b733ab (修复了一个小问题，增加自定义注解的操作日志，方便对单独的方法进行监控)
public class ThreadLocalUtil {
    //提供ThreadLocal对象,
    private static final ThreadLocal THREAD_LOCAL = new ThreadLocal();

    //根据键获取值
    public static <T> T get() {
        return (T) THREAD_LOCAL.get();
    }

    //存储键值对
    public static void set(Object value) {
        THREAD_LOCAL.set(value);
    }


    //清除ThreadLocal 防止内存泄漏
    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
