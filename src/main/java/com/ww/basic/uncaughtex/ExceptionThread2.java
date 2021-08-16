package com.ww.basic.uncaughtex;

/**
 * @author xiaohua
 * @description
 * @date 2021-8-15 7:44
 */
public class ExceptionThread2 implements Runnable {

    @Override
    public void run() {
        Thread thread = Thread.currentThread();
        System.out.println("run() by " + thread);
        System.out.println("eh = " + thread.getUncaughtExceptionHandler());

        throw new RuntimeException();
    }
}
