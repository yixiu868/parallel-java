package com.ww.juc;

import java.util.concurrent.Semaphore;

/**
 * Semaphore实现生产者消费者
 *
 * put()和get()方法的调用顺序是通过两个信号量处理的: semProd和semCon。put()方法在能够产生数值之前，必须从semProd获得许可证。
 * 在设置数值之后，释放semCon。get()方法在能够使用数值之前，必须从semCom获得许可证。使用完数值之后，释放semProd。这种“给予与获取”
 * 机制确保对put()方法的每次调用，后面都跟随相应的get()调用。
 *
 * 注意：semCon最初被初始化为没有可用许可证。这样可以保证首先执行put()。设置初始同步状态的能力是信号量功能更为强大的一个方面
 *
 * @author wanggw
 * @date 2022-01-27 09:06:10
 */
public class SemaphoreDemo03 {

    public static void main(String[] args) {
        Q q = new Q();
        new Consumer(q);
        new Producer(q);
    }
}

class Q {

    int n;

    // semCon
    static Semaphore semCon = new Semaphore(0);
    static Semaphore semProd = new Semaphore(1);

    void get() {
        try {
            semCon.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Got: " + n);
        semProd.release();
    }

    void put(int n) {
        try {
            semProd.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.n = n;
        System.out.println("Put: " + n);
        semCon.release();
    }
}

/**
 * 生产者
 */
class Producer implements Runnable {

    Q q;

    Producer(Q q) {
        this.q = q;
        new Thread(this, "Producer").start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            q.put(i);
        }
    }
}


class Consumer implements Runnable {

    Q q;

    Consumer(Q q) {
        this.q = q;
        new Thread(this, "Consumer").start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            q.get();
        }
    }
}