package com.ww;

/**
 * @author xiaohua
 * @date 2021-3-31 16:44
 * @description 两个线程轮流打印数字，一直打100
 */
public class Question02 {

    public static void main(String[] args) {
        TakeTurns takeTurns = new TakeTurns();
        new Thread(new Runnable() {
            @Override
            public void run() {
                takeTurns.print1();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                takeTurns.print2();
            }
        }).start();
    }

    static class TakeTurns {

        private static boolean flag = true;

        private static int count = 0;

        public synchronized void print1() {
            for (int i = 1; i <= 50; i++) {
                while (!flag) {
                    try {
                        // System.out.println("print1: wait before");
                        wait();
                        // System.out.println("print1: wait after");
                    } catch (Exception e) {

                    }
                }

                System.out.println("print1: " + ++count);
                flag = !flag;
                notifyAll();
            }
        }

        public synchronized void print2() {
            for (int i = 1; i <= 50; i++) {
                while (flag) {
                    try {
                        // System.out.println("print2: wait before");
                        wait();
                        // System.out.println("print2: wait after");
                    } catch (Exception e) {

                    }
                }

                System.out.println("print2: " + ++count);
                flag = !flag;
                notifyAll();
            }
        }
    }
}
