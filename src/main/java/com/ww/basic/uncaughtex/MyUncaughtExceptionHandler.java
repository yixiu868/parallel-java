package com.ww.basic.uncaughtex;

/**
 * @author xiaohua
 * @description 实现Thread.UncaughtExceptionHandler，创建异常处理器
 * @date 2021-8-15 7:46
 */
public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("caught " + e);
    }
}
