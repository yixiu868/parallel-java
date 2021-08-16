package com.ww.basic.uncaughtex;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xiaohua
 * @description 捕获线程异常
 * @date 2021-8-15 7:40
 */
public class ExceptionThread implements Runnable {

    @Override
    public void run() {
        throw new RuntimeException();
    }

    /**
     * 任务在run方法执行期间抛出一个异常，并且这个异常会抛到run方法外面，main方法无法对它进行捕获
     * Exception in thread "pool-1-thread-1" java.lang.RuntimeException
     * 	at com.ww.basic.uncaughtex.ExceptionThread.run(ExceptionThread.java:15)
     * 	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
     * 	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
     * 	at java.base/java.lang.Thread.run(Thread.java:834)
     * @param args
     */
    public static void main(String[] args) {
        try {
            ExecutorService service = Executors.newCachedThreadPool();
            service.execute(new ExceptionThread());
        } catch (Exception e) {
            System.out.println("eeee");
        }
    }
}
