package com.ww;

/**
 * @author xiaohua
 * @date 2021-3-31 17:41
 * @description 生产者消费者 synchronized方式
 */
public class Question07_1 {

    private final static String LOCK = "lock";

    private int count = 0;

    private static final int FULL = 10;

    public static void main(String[] args) {
        Question07_1 question07_1 = new Question07_1();
        for (int i = 1; i <= 5; i++) {
            new Thread(question07_1.new Producer(), "生产者-" + i).start();
            new Thread(question07_1.new Consumer(), "消费者-" + i).start();
        }
    }

    class Producer implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                synchronized (LOCK) {
                    while (FULL == count) {
                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("生产者" + Thread.currentThread().getName() + "生产后, 总共有" + ++count + "个资源.");
                    LOCK.notifyAll();
                }
            }
        }
    }

    class Consumer implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                synchronized (LOCK) {
                    while (0 == count) {
                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println("消费者" + Thread.currentThread().getName() + "消费后, 总共有" + --count + "个资源");
                    LOCK.notifyAll();
                }
            }
        }
    }
}
