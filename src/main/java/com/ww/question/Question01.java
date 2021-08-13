package com.ww.question;

/**
 * @author xiaohua
 * @date 2021-3-31 16:39
 * @description 要求线程a执行完成才开始线程b，线程b执行完成才开始线程c
 */
public class Question01 {

    public static void main(String[] args) {
        PrintThread a = new PrintThread("线程a");
        PrintThread b = new PrintThread("线程b");
        PrintThread c = new PrintThread("线程c");

        try {
            System.out.println("线程a开始执行...");
            a.start();
            a.join();
            System.out.println("线程a执行完成.");

            System.out.println("线程b开始执行...");
            b.start();
            b.join();
            System.out.println("线程b执行完成.");

            System.out.println("线程c开始执行...");
            c.start();
            c.join();
            System.out.println("线程c执行完成.");
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
            for (int i = 0; i < 10; i++) {
                System.out.println(getName() + ": " + i);
            }
        }
    }
}
