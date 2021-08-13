package com.ww.question;

/**
 * @author xiaohua
 * @date 2021-3-31 16:44
 * @description 两个线程轮流打印数字，一直打100
 */
public class Question02 {

    public static void main(String[] args) {
        TakeTurns takeTurns = new TakeTurns();
        new Thread(() -> takeTurns.print1(), "线程1").start();
        new Thread(() -> takeTurns.print2(), "线程2").start();
    }

    static class TakeTurns {

        private static boolean flag = true;

        private static int count = 0;

        // flag为false, print1()等待中, 被唤醒后再次判断flag为true, 执行打印操作
        public synchronized void print1() {
            for (int i = 1; i <= 50; i++) {
                while (!flag) {
                    try {
                        // System.out.println("print1: wait before");
                        wait();
                        // System.out.println("print1: wait after");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                System.out.println(Thread.currentThread().getName() + "-print1: " + ++count);
                flag = !flag;
                notifyAll();
            }
        }

        // flag为true, print2()等待中, 被唤醒后再次判断flag为false, 执行打印操作
        public synchronized void print2() {
            for (int i = 1; i <= 50; i++) {
                while (flag) {
                    try {
                        // System.out.println("print2: wait before");
                        wait();
                        // System.out.println("print2: wait after");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                System.out.println(Thread.currentThread().getName() + "-print2: " + ++count);
                flag = !flag;
                notifyAll();
            }
        }
    }
}
