package com.ww.lock.condition;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wanggw
 * @Date 2022年02月28 23:23
 */
public class BoundedQueue<T> {

    private Object[] items;

    private int addIndex, removeIndex, count;

    private Lock lock = new ReentrantLock();

    private Condition notEmpty = lock.newCondition();

    private Condition notFull = lock.newCondition();

    public BoundedQueue(int size) {
        items = new Object[size];
    }

    /**
     * 添加元素，如果数组满，则添加线程进入等待状态，知道有空位
     * @param t
     * @throws InterruptedException
     */
    public void add(T t) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await();
            }
            items[addIndex] = t;
            if (++addIndex == items.length) {
                addIndex = 0;
            }
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 由头部删除一个元素，如果数组为空，则删除线程进入等待状态，直到有元素添加
     * @return
     * @throws InterruptedException
     */
    @SuppressWarnings("unchecked")
    public T remove() throws InterruptedException {
        lock.lock();
        try {
            while (0 == count) {
                notEmpty.await();
            }
            Object x = items[removeIndex];
            if (++removeIndex == items.length) {
                removeIndex = 0;
            }
            --count;
            notFull.signal();
            return (T) x;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        BoundedQueue<Integer> queue = new BoundedQueue<Integer>(1);
        new Thread(new Producer(queue)).start();
        new Thread(new Consumer(queue)).start();
    }
}

class Producer implements Runnable {

    private BoundedQueue<Integer> queue;

    public Producer(BoundedQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (;;) {
            SecureRandom secureRandom = new SecureRandom();
            try {
                Integer value = (secureRandom.nextInt(10000));
                System.out.println("[生产者]产生" + value);
                queue.add(value);
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable {

    private BoundedQueue<Integer> queue;

    public Consumer(BoundedQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (;;) {
            try {
                Integer value = queue.remove();
                System.out.println("[消费者]获取到" + value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
