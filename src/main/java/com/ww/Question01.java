package com.ww;

/**
 * @author xiaohua
 * @date 2021-3-31 16:39
 * @description 要求线程a执行完成才开始线程b，线程b执行完成才开始线程c
 */
public class Question01 {

    public static void main(String[] args) {
        PrintThread a = new PrintThread("a");
        PrintThread b = new PrintThread("b");
        PrintThread c = new PrintThread("c");

        try {
            a.start();
            a.join();

            b.start();
            b.join();

            c.start();
            c.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static class PrintThread extends Thread {
        PrintThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                System.out.println(getName() + ": " + i);
            }
        }
    }
}
