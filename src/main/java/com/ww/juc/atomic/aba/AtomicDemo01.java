package com.ww.juc.atomic.aba;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xiaohua
 * @description 模拟ABA问题
 * @date 2021-8-25 11:46
 */
public class AtomicDemo01 {

    private static AtomicInteger index = new AtomicInteger(20);

    public static void main(String[] args) {

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "先把20修改为11");
            index.compareAndSet(20, 11);
            System.out.println(Thread.currentThread().getName() + "再把11改回20");
            index.compareAndSet(11, 20);
            System.out.println(Thread.currentThread().getName() + "修改过程：20->11->20");
            System.out.println("-------------------------");
        }, "詹三").start();


        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                boolean isSuccess = index.compareAndSet(20, 12);
                System.out.println(Thread.currentThread().getName() + ", index预期值20, 修改是否成功" + isSuccess + ", 修改后值为" + index.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "李思").start();
    }
}
