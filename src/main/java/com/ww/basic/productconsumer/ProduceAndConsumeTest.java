package com.ww.basic.productconsumer;

import java.util.concurrent.TimeUnit;

/**
 * @author xiaohua
 * @description 生产消费测试程序
 * @date 2021-8-13 11:11
 */
public class ProduceAndConsumeTest {

    public static void main(String[] args) throws InterruptedException {
        AbstractStorage storage = new ConcreteStorage();

        Producer producer1 = new Producer(storage);
        Producer producer2 = new Producer(storage);
        Producer producer3 = new Producer(storage);
        Producer producer4 = new Producer(storage);
        Producer producer5 = new Producer(storage);
        Producer producer6 = new Producer(storage);
        Producer producer7 = new Producer(storage);

        Consumer consumer1 = new Consumer(storage);
        Consumer consumer2 = new Consumer(storage);
        Consumer consumer3 = new Consumer(storage);
        Consumer consumer4 = new Consumer(storage);

        producer1.setNum(10);
        producer2.setNum(10);
        producer3.setNum(10);
        producer4.setNum(10);
        producer5.setNum(10);
        producer6.setNum(10);
        producer7.setNum(10);

        consumer1.setNum(20);
        consumer2.setNum(10);
        consumer3.setNum(30);
        consumer4.setNum(10);

        producer1.start();
        producer2.start();
        producer3.start();
        producer4.start();
        producer5.start();
        producer6.start();
        producer7.start();

        consumer1.start();
        consumer2.start();
        consumer3.start();
        consumer4.start();

        TimeUnit.SECONDS.sleep(20);
    }
}
