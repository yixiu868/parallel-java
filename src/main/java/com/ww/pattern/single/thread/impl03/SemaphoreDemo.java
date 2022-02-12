package com.ww.pattern.single.thread.impl03;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaohua
 * @description Semaphore使用练习
 * @date 2021-10-12 12:43
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        BoundedResource resource = new BoundedResource(3);

        for (int i = 0; i < 10; i++) {
            new UserThread(resource).start();
        }
    }
}

class Log {

    public static void println(String s) {
        System.out.println(Thread.currentThread().getName() + ": " + s);
    }
}

class BoundedResource {

    private final Semaphore semaphore;

    private final int permits;

    private final static Random random = new Random(314159);

    public BoundedResource(int permits) {
        this.semaphore = new Semaphore(permits);
        this.permits = permits;
    }

    public void use() throws InterruptedException {
        semaphore.acquire();
        try {
            doUse();
        } finally {
            semaphore.release();
        }
    }

    protected void doUse() throws InterruptedException {
        Log.println("BEGIN: used = " + (permits - semaphore.availablePermits()));
        TimeUnit.MILLISECONDS.sleep(random.nextInt(500));
        Log.println("END: used = " + (permits - semaphore.availablePermits()));
    }
}

class UserThread extends Thread {

    private final static Random random = new Random(26535);

    private final BoundedResource resource;

    public UserThread(BoundedResource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        try {
            while (true) {
                resource.use();
                TimeUnit.MILLISECONDS.sleep(random.nextInt(3000));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}