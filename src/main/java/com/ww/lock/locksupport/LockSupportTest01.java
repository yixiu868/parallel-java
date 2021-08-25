package com.ww.lock.locksupport;

import java.util.concurrent.locks.LockSupport;

/**
 * @author xiaohua
 * @description TODO
 * @date 2021-8-24 15:27
 */
public class LockSupportTest01 {

    private static Thread mainThread;

    public static void main(String[] args) {
        ThreadA ta = new ThreadA("ta");
        mainThread = Thread.currentThread();

        System.out.println(Thread.currentThread().getName() + " start ta");
        ta.start();

        System.out.println(Thread.currentThread().getName() + " block");
        LockSupport.park(mainThread);

        System.out.println(Thread.currentThread().getName() + " continue");
    }


    static class ThreadA extends Thread {

        public ThreadA(String name) {
            super(name);
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " wakeup others");
            LockSupport.unpark(mainThread);
        }
    }
}
