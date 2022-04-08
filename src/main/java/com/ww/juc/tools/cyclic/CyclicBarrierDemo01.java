package com.ww.juc.tools.cyclic;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author xiaohua
 * @description 墙倒众人推，人齐了才能推倒，团结就是力量
 * @date 2021-8-24 17:47
 */
public class CyclicBarrierDemo01 {

    public static void main(String[] args) {
//        CyclicBarrier barrier = new CyclicBarrier(3);
        CyclicBarrier barrier = new CyclicBarrier(3, () -> {
            try {
                System.out.println("等裁判吹口哨...");
                TimeUnit.SECONDS.sleep(2);
                System.out.println("裁判吹口>>>>>>>>>>");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Runner runner1 = new Runner(barrier, "张三");
        Runner runner2 = new Runner(barrier, "李四");
        Runner runner3 = new Runner(barrier, "王五");

        ExecutorService service = Executors.newFixedThreadPool(3);
        service.submit(runner1);
        service.submit(runner2);
        service.submit(runner3);

        service.shutdown();
    }

    static class Runner implements Runnable {

        private CyclicBarrier barrier;

        private String name;

        public Runner(CyclicBarrier barrier, String name) {
            this.barrier = barrier;
            this.name = name;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(new Random().nextInt(5));
                System.out.println(name + "：准备OK");
                barrier.await();
                System.out.println(name + "：开跑");
                System.out.println("------------------");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
