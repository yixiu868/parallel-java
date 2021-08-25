package com.ww.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaohua
 * @description 自定义线程池实现
 * @date 2021-8-25 10:35
 */
public class ThreadPoolExecutorDemo01 {

    private static final int CORE_POOL_SIZE = 5;

    private static final int MAX_POOL_SIZE = 10;

    private static final int QUEUE_CAPACITY = 100;

    private static final long KEEP_ALIVE_TIME = 1L;

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 0; i < 10; i++) {
            MyRunnable runnable = new MyRunnable(String.valueOf(i));
            executor.execute(runnable);
        }

        // 终止线程池
        executor.shutdown();

        while (!executor.isTerminated()) {
        }

        System.out.println("结束所有线程.");
    }
}
