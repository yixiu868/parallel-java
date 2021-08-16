package com.ww.basic.priority;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xiaohua
 * @description 线程优先级练习
 * @date 2021-8-15 7:28
 */
public class SimplePriorities implements Runnable {

    private int priority;

    public SimplePriorities(int priority) {
        this.priority = priority;
    }

    @Override
    public void run() {
        // 设置线程优先级
        Thread.currentThread().setPriority(priority);
        for (int i = 0; i < 1; i++) {
            System.out.println(this);
            if (i % 10 == 0) {
                Thread.yield();
            }
        }
    }

    @Override
    public String toString() {
        return "SimplePriorities{" +
                "thread=" + Thread.currentThread() +
                "priority=" + priority +
                '}';
    }

    /**
     * 执行结果
     * SimplePriorities{thread=Thread[pool-1-thread-1,10,main]priority=10}
     * SimplePriorities{thread=Thread[pool-1-thread-5,10,main]priority=10}
     * SimplePriorities{thread=Thread[pool-1-thread-4,10,main]priority=10}
     * SimplePriorities{thread=Thread[pool-1-thread-2,10,main]priority=10}
     * SimplePriorities{thread=Thread[pool-1-thread-3,10,main]priority=10}
     * SimplePriorities{thread=Thread[pool-1-thread-6,1,main]priority=1}
     * @param args
     */
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            service.execute(new SimplePriorities(Thread.MAX_PRIORITY));
        }
        service.execute(new SimplePriorities(Thread.MIN_PRIORITY));

        service.shutdown();
    }
}
