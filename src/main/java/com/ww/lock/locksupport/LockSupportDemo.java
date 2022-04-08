package com.ww.lock.locksupport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author wanggw
 * @Date 2022年02月27 14:22
 */
public class LockSupportDemo {

    private static Object u = new Object();

    static ChangeObjectThread t1 = new ChangeObjectThread("t1");

    static ChangeObjectThread t2 = new ChangeObjectThread("t2");

    public static class ChangeObjectThread extends Thread {

        public ChangeObjectThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (u) {
                System.out.println("在线程" + getName() + "中");
                LockSupport.park();
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("线程" + getName() + "被中断了");
                }
                System.out.println("线程" + getName() + "继续执行了");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 打印结果
//        主线程睡眠1s
//        在线程t1中
//        主线程睡眠3s
//        线程t1被中断...
//        线程t2被unpark()了
//        线程t1被中断了
//        线程t1继续执行了
//        在线程t2中
//        线程t2继续执行了

        t1.start();
        System.out.println("主线程睡眠1s");
        TimeUnit.SECONDS.sleep(1);
        t2.start();
        System.out.println("主线程睡眠3s");
        TimeUnit.SECONDS.sleep(3);
        System.out.println("线程" + t1.getName() + "被中断...");
        t1.interrupt();
        System.out.println("线程" + t2.getName() + "被unpark()了");
        LockSupport.unpark(t2);
        t1.join();
        t2.join();
    }
}
