package com.ww.question;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiaohua
 * @date 2021-3-31 18:05
 * @description ReentrantLock方式实现生产者、消费者（可以保证顺序）
 */
public class Question07_2 {

    private int count = 0;

    private final static int FULL = 10;

    private Lock lock;

    private Condition notEmptyCondition;

    private Condition notFullCondition;

    {
        lock = new ReentrantLock();
        notEmptyCondition = lock.newCondition();
        notFullCondition = lock.newCondition();
    }

    public static void main(String[] args) {
        Question07_2 question07_2 = new Question07_2();

        for (int i = 1; i < 5; i++) {
            new Thread(question07_2.new Producer(), "生产者-" + i).start();
            new Thread(question07_2.new Consumer(), "消费者-" + i).start();
        }
    }

    class Producer implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                lock.lock();

                try {
                    while (FULL == count) {
                        try {
                            notFullCondition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println("生产者" + Thread.currentThread().getName() + "生产后, 总共有" + ++count + "个资源");
                    notEmptyCondition.signal();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    class Consumer implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {

                lock.lock();
                try {
                    while (0 == count) {
                        notEmptyCondition.await();
                    }
                    System.out.println("消费者" + Thread.currentThread().getName() + "消费后, 总共有" + --count + "个资源");
                    notFullCondition.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
