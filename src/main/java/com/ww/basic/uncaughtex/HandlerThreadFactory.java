package com.ww.basic.uncaughtex;

import java.util.concurrent.ThreadFactory;

/**
 * @author xiaohua
 * @description TODO
 * @date 2021-8-15 7:47
 */
public class HandlerThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        System.out.println(this + " creating new Thread");
        Thread thread = new Thread(r);
        System.out.println("created " + thread);
        thread.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        System.out.println("ex = " + thread.getUncaughtExceptionHandler());
        return thread;
    }
}
