package com.ww.juc.tools.semaphore;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaohua
 * @description Semaphore 信号量，用来控制同一时间，资源可被访问的线程数量，一般可用于流量的控制
 * Semaphore需要拿到许可才能执行，并可以选择公平和非公平模式
 * @date 2021-8-24 18:02
 */
public class SemaphoreDemo01 {

    private static int count = 20;

    public static void main(String[] args) throws NoSuchAlgorithmException {
        ExecutorService service = Executors.newFixedThreadPool(10);

        // 指定最多只能有5个线程同时执行
        Semaphore semaphore = new Semaphore(5, true);

        Random random = SecureRandom.getInstanceStrong();
        for (int i = 0; i < count; i++) {
            final int no = i;
            service.submit(() -> {
                try {
                    // 获得许可
                    semaphore.acquire();
                    System.out.println(no + "：号车可同行");
                    TimeUnit.SECONDS.sleep(random.nextInt(2));
                    // 释放许可证
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        service.shutdown();
    }
}
