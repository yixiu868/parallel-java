package com.ww.basic;

import java.util.concurrent.TimeUnit;

/**
 * @author xiaohua
 * @description 两种方式创建线程区别
 * @date 2021-9-17 22:58
 */
public class ThreadCreationCmp {

    public static void main(String[] args) {
        Thread t;

        CountingTask task = new CountingTask();

        final int num = Runtime.getRuntime().availableProcessors();

        System.out.println("cpu数量：" + num);

        // Runnable方法启动新线程
        for (int i = 0; i < 2 * num; i++) {
            t = new Thread(task);
            t.start();
        }

        // Thread方式启动新线程
        for (int i = 0; i < 2 * num; i++) {
            t = new CountingThread();
            t.start();
        }
    }

    /**
     * 计数器
     */
    static class Counter {

        private int count = 0;

        public void increment() {
            count++;
        }

        public int value() {
            return count;
        }
    }

    /**
     * 任务线程
     */
    static class CountingTask implements Runnable {

        private Counter counter = new Counter();

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                // 执行具体任务
                doSomething();
                // 计数器
                counter.increment();
            }

            System.out.println("CountingTask:" + counter.value());
        }

        private void doSomething() {
            try {
                TimeUnit.MILLISECONDS.sleep(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 工作线程
     */
    static class CountingThread extends Thread {

        private Counter counter = new Counter();

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                doSomething();
                counter.increment();
            }
            System.out.println("CountingThread:" + counter.value());
        }

        private void doSomething() {
            try {
                TimeUnit.MILLISECONDS.sleep(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
