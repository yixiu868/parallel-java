package com.ww.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author xiaohua
 * @description TODO
 * @date 2021-8-24 21:40
 */
public class SafeStopThread {

    public static void main(String[] args) {
        stopByFlag();
    }

    static void stopByFlag() {
        ARunnable aRunnable = new ARunnable();
        new Thread(aRunnable).start();
        aRunnable.tellToStop();
    }

    static class ARunnable implements Runnable {
        volatile boolean stop;

        void tellToStop() {
            stop = true;
        }

        @Override
        public void run() {
            System.out.println("进入不可停止区域1...");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("退出不可停止区域1...");
            System.out.printf("检测停止标志stop = %s\n", String.valueOf(stop));
            if (stop) {
                System.out.println("停止执行");
                return;
            }
            System.out.println("进入不可停止区域2...");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("退出不可停止区域2...");
        }
    }
}
