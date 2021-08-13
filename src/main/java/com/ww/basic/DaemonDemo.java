package com.ww.basic;

import java.util.concurrent.TimeUnit;

/**
 * @author xiaohua
 * @description 守护线程示例
 * @date 2021-8-13 15:21
 */
public class DaemonDemo {

    public static class DaemonT extends Thread {

        @Override
        public void run() {
            while (true) {
                System.out.println("alive thread");
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DaemonT daemonT = new DaemonT();
//        daemonT.setDaemon(true);
        daemonT.start();

        TimeUnit.SECONDS.sleep(2);
    }
}
