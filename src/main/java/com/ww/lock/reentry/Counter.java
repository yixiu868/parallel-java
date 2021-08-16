package com.ww.lock.reentry;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiaohua
 * @description 可重入锁使用示例
 * @date 2021-8-14 16:01
 */
public class Counter {

    // 重入锁
    private final Lock lock = new ReentrantLock();

    private int count;

    public void incr() {
        // 访问count时，需要加锁
        lock.lock();

        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

    public int getCount() {
        // 读取数据也需要加锁
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }
}
