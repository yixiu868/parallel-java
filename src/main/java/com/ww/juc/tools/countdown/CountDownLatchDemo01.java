package com.ww.juc.tools.countdown;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaohua
 * @description CountDownLatch用例
 * @date 2021-8-24 17:36
 */
public class CountDownLatchDemo01 {

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 张三开始修复bug，当前时间：2021-08-24 17:43:27
     * 李四开始修复bug，当前时间：2021-08-24 17:43:27
     * 张三结束修复bug，当前时间：2021-08-24 17:43:29
     * 李四结束修复bug，当前时间：2021-08-24 17:43:30
     * bug全部解决，共用时：3
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        Worker worker1 = new Worker("张三", 2, latch);
        Worker worker2 = new Worker("李四", 3, latch);

        worker1.start();
        worker2.start();

        long start = System.currentTimeMillis();
        latch.await();
        System.out.println("bug全部解决，共用时：" + (System.currentTimeMillis() - start) / 1000);
    }

    static class Worker extends Thread {

        private String name;

        private int workTime;

        private CountDownLatch latch;

        public Worker(String name, int workTime, CountDownLatch latch) {
            this.name = name;
            this.workTime = workTime;
            this.latch = latch;
        }

        @Override
        public void run() {
            System.out.println(name + "开始修复bug，当前时间：" + sdf.format(new Date()));
            doWork();
            System.out.println(name + "结束修复bug，当前时间：" + sdf.format(new Date()));
            latch.countDown();
        }

        private void doWork() {
            try {
                // 模拟工作耗时
                TimeUnit.SECONDS.sleep(workTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
