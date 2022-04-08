package com.ww.question;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 阻塞队列实现生产者、消费者
 * @author xiaohua
 * @date 2021-3-31 18:22
 */
public class Question07_3 {

    private int count = 0;

    private BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

    public static void main(String[] args) {
        Question07_3 question07_3 = new Question07_3();
        for (int i = 1; i <= 5; i++) {
            new Thread(question07_3.new Producer(), "生产者-" + i).start();
            new Thread(question07_3.new Consumer(), "消费者-" + i).start();
        }
    }

    /**
     * 生产者
     */
    class Producer implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    queue.put(1);
                    System.out.println("生产者" + Thread.currentThread().getName() + "生产后, 总共有" + ++count + "个资源");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 消费者
     */
    class Consumer implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    queue.take();
                    System.out.println("消费者" + Thread.currentThread().getName() + "消费后, 总共有" + --count + "个资源");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
