package com.ww.lock.locksupport;

/**
 * @author xiaohua
 * @description TODO
 * @date 2021-8-24 15:23
 */
public class WaitTest01 {

    public static void main(String[] args) {
        ThreadA threadA = new ThreadA("a");

        synchronized (threadA) {
            try {
                System.out.println(Thread.currentThread().getName() + " start threadA");
                threadA.start();

                System.out.println(Thread.currentThread().getName() + " block");
                // 主线程等待
                threadA.wait();

                System.out.println(Thread.currentThread().getName() + " continue");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class ThreadA extends Thread {

        public ThreadA(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (this) {
                System.out.println(Thread.currentThread().getName() + " wakeup others");
                notify();
            }
        }
    }
}
