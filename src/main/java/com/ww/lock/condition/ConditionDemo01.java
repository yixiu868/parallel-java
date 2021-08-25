package com.ww.lock.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiaohua
 * @description Condition的作用是对锁进行更精确的控制。Condition中的await()方法相当于Object的wait()方法，Condition中的signal()方法相当于notify()方法，
 * Condition中的signalAll()相当于Object的notifyAll()方法，Condition需要与“互斥锁/共享锁”捆绑使用
 * @date 2021-8-24 15:45
 */
public class ConditionDemo01 {

    private static Lock lock = new ReentrantLock();

    private static Condition condition = lock.newCondition();

    public static void main(String[] args) {
        ThreadA ta = new ThreadA("ta");

        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "开始启动");
            ta.start();

            System.out.println(Thread.currentThread().getName() + "线程阻塞");
            condition.await();

            System.out.println(Thread.currentThread().getName() + "继续执行");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    static class ThreadA extends Thread {

        public ThreadA(String name) {
            super(name);
        }

        @Override
        public void run() {
            lock.lock();
            try {
                System.out.println("线程" + Thread.currentThread().getName() + "唤醒其他线程");
                condition.signal();
            } finally {
                lock.unlock();
            }
        }
    }
}
