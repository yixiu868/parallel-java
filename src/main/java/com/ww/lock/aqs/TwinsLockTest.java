package com.ww.lock.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class TwinsLockTest {

    public static void test() throws InterruptedException {
        final Lock lock = new TwinsLock();

        class Worker extends Thread {

            @Override
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println(Thread.currentThread().getName());
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }

        for (int i = 0; i < 10; i++) {
            Worker worker = new Worker();
            worker.setDaemon(true);
            worker.start();
        }

        // 每隔1秒换行
        for (int i = 0; i < 10; i++) {
            TimeUnit.SECONDS.sleep(1);
            System.out.println();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        test();
    }
}
