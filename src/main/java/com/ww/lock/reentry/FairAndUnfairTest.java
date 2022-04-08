package com.ww.lock.reentry;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平、非公平可重入锁
 */
public class FairAndUnfairTest {

    private static Lock fairLock = new ReentrantLock2(true);

    private static Lock unfairLock = new ReentrantLock2(false);

    private static CountDownLatch start;

    public void fair() {
        testLock(fairLock);
    }

    public void unfair() {
        testLock(unfairLock);
    }

    private void testLock(Lock lock) {
        // 先拦住所有job，不让跑
        start = new CountDownLatch(1);
        for (int i = 0; i < 5; i++) {
            Job job = new Job(lock);
            job.setName("线程" + i);
            job.start();
        }
        // 所有job线程同时开始抢占执行
        start.countDown();
    }

    private static class Job extends Thread {

        private Lock lock;

        public Job(Lock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                start.await();
                System.out.println(getName() + "此刻" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + "等待中...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 当前线程尝试执行两次
            for (int i = 0; i < 2; i++) {
                lock.lock();
                System.out.println(getName() + "此刻" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + "开始执行...");
                try {
                    System.out.println("Lock by [" + getName() + "], waiting by" + ((ReentrantLock2)lock).getQueuedThreads());
                } finally {
                    System.out.println(getName() + "此刻" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + "执行结束.");
                    lock.unlock();
                }
            }
        }

        @Override
        public String toString() {
           return getName();
        }
    }

    private static class ReentrantLock2 extends ReentrantLock {

        /**
         * 
         */
        private static final long serialVersionUID = 501449188564680882L;

        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        @Override
        protected Collection<Thread> getQueuedThreads() {
            List<Thread> arrayList = new ArrayList<>(super.getQueuedThreads());
            Collections.reverse(arrayList);
            return arrayList;
        }
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        FairAndUnfairTest test = new FairAndUnfairTest();
//        公平锁打印结果
//        线程4此刻2022-02-27 11:17:34.931等待中...
//        线程0此刻2022-02-27 11:17:34.931等待中...
//        线程1此刻2022-02-27 11:17:34.931等待中...
//        线程2此刻2022-02-27 11:17:34.931等待中...
//        线程4此刻2022-02-27 11:17:34.932开始执行...
//        Lock by [线程4], waiting by[线程0, 线程1, 线程2]
//        线程4此刻2022-02-27 11:17:34.933执行结束.
//        线程0此刻2022-02-27 11:17:34.933开始执行...
//        Lock by [线程0], waiting by[线程1, 线程2, 线程4]
//        线程0此刻2022-02-27 11:17:34.933执行结束.
//        线程1此刻2022-02-27 11:17:34.933开始执行...
//        Lock by [线程1], waiting by[线程2, 线程4, 线程0]
//        线程1此刻2022-02-27 11:17:34.933执行结束.
//        线程2此刻2022-02-27 11:17:34.934开始执行...
//        Lock by [线程2], waiting by[线程4, 线程0, 线程1]
//        线程2此刻2022-02-27 11:17:34.934执行结束.
//        线程4此刻2022-02-27 11:17:34.934开始执行...
//        Lock by [线程4], waiting by[线程0, 线程1, 线程2]
//        线程4此刻2022-02-27 11:17:34.934执行结束.
//        线程0此刻2022-02-27 11:17:34.935开始执行...
//        Lock by [线程0], waiting by[线程1, 线程2]
//        线程0此刻2022-02-27 11:17:34.935执行结束.
//        线程1此刻2022-02-27 11:17:34.935开始执行...
//        Lock by [线程1], waiting by[线程2]
//        线程1此刻2022-02-27 11:17:34.936执行结束.
//        线程2此刻2022-02-27 11:17:34.936开始执行...
//        Lock by [线程2], waiting by[]
//        线程2此刻2022-02-27 11:17:34.936执行结束.
//        线程3此刻2022-02-27 11:17:34.937等待中...
//        线程3此刻2022-02-27 11:17:34.937开始执行...
//        Lock by [线程3], waiting by[]
//        线程3此刻2022-02-27 11:17:34.937执行结束.
//        线程3此刻2022-02-27 11:17:34.938开始执行...
//        Lock by [线程3], waiting by[]
//        线程3此刻2022-02-27 11:17:34.938执行结束.
//        test.fair();

//        非公平锁打印结果
//        线程2此刻2022-02-27 11:04:43.063等待中...
//        线程4此刻2022-02-27 11:04:43.063等待中...
//        线程0此刻2022-02-27 11:04:43.063等待中...
//        线程3此刻2022-02-27 11:04:43.063等待中...
//        线程1此刻2022-02-27 11:04:43.063等待中...
//        线程2此刻2022-02-27 11:04:43.064开始执行...
//        Lock by [线程2], waiting by[线程4, 线程0, 线程3, 线程1]
//        线程2此刻2022-02-27 11:04:43.064执行结束.
//        线程2此刻2022-02-27 11:04:43.064开始执行...
//        Lock by [线程2], waiting by[线程4, 线程0, 线程3, 线程1]
//        线程2此刻2022-02-27 11:04:43.064执行结束.
//        线程4此刻2022-02-27 11:04:43.064开始执行...
//        Lock by [线程4], waiting by[线程0, 线程3, 线程1]
//        线程4此刻2022-02-27 11:04:43.065执行结束.
//        线程4此刻2022-02-27 11:04:43.065开始执行...
//        Lock by [线程4], waiting by[线程0, 线程3, 线程1]
//        线程4此刻2022-02-27 11:04:43.065执行结束.
//        线程0此刻2022-02-27 11:04:43.065开始执行...
//        Lock by [线程0], waiting by[线程3, 线程1]
//        线程0此刻2022-02-27 11:04:43.065执行结束.
//        线程0此刻2022-02-27 11:04:43.066开始执行...
//        Lock by [线程0], waiting by[线程3, 线程1]
//        线程0此刻2022-02-27 11:04:43.066执行结束.
//        线程3此刻2022-02-27 11:04:43.066开始执行...
//        Lock by [线程3], waiting by[线程1]
//        线程3此刻2022-02-27 11:04:43.066执行结束.
//        线程3此刻2022-02-27 11:04:43.066开始执行...
//        Lock by [线程3], waiting by[线程1]
//        线程3此刻2022-02-27 11:04:43.067执行结束.
//        线程1此刻2022-02-27 11:04:43.067开始执行...
//        Lock by [线程1], waiting by[]
//        线程1此刻2022-02-27 11:04:43.067执行结束.
//        线程1此刻2022-02-27 11:04:43.067开始执行...
//        Lock by [线程1], waiting by[]
//        线程1此刻2022-02-27 11:04:43.067执行结束.
//        test.unfair();

//        为什么会出现连续获取锁的情况？
//        当一个线程请求锁时，只要获取了同步状态即成功获取锁。在这个前提下，刚释放锁的线程再次获取同步状态几率非常大，
//        使得其他线程只能在同步队列中等待。
    }
}
