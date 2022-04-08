package com.ww.juc.tools.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author wanggw
 * @date 2022-01-26 16:48:51
 */
public class SemaphoreDemo02 {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);

        new IncThread(semaphore, "A");
        new DecThread(semaphore, "B");
    }
}

class Shared {
    static int count = 0;
}

class IncThread implements Runnable {

    String name;
    Semaphore sem;

    IncThread(Semaphore sem, String name) {
        this.sem = sem;
        this.name = name;
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println("Starting " + name);

        try {
            System.out.println(name + " is waiting for a permit.");
            // 获取许可
            sem.acquire();
            System.out.println(name + " gets a permit.");

            for (int i = 0; i < 5; i++) {
                Shared.count++;
                System.out.println(name + ": " + Shared.count);
                TimeUnit.MILLISECONDS.sleep(10);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 释放许可
        System.out.println(name + " releases the permit.");
        sem.release();
    }
}

class DecThread implements Runnable {

    String name;
    Semaphore sem;
    
    DecThread(Semaphore sem, String name) {
        this.sem = sem;
        this.name = name;
        new Thread(this).start();
    }
    
    @Override
    public void run() {
        System.out.println(name + " is waiting for a permit.");
        try {
            sem.acquire();
            System.out.println(name + " gets a permit.");

            for (int i = 0; i < 5; i++) {
                Shared.count--;
                System.out.println(name + ": " + Shared.count);
                TimeUnit.MILLISECONDS.sleep(10);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(name + " releases the permit.");
        sem.release();
    }
}