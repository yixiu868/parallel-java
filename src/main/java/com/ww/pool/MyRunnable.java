package com.ww.pool;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaohua
 * @description 线程池示例
 * @date 2021-8-25 10:32
 */
public class MyRunnable implements Runnable {

    private String command;

    public MyRunnable(String s) {
        this.command = s;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "开始时间" + new Date());
        processCommand();
        System.out.println(Thread.currentThread().getName() + "结束时间" + new Date());
    }

    private void processCommand() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "MyRunnable{" +
                "command='" + command + '\'' +
                '}';
    }
}
