package com.ww;

/**
 * @author xiaohua
 * @date 2021-3-31 17:11
 * @description 编写一个程序，启动三个线程，三个线程ID分别是A、B、C，每个线程将自己的ID值在屏幕上打印5遍，打印顺序是ABCABC...
 */
public class Question04 {

    private int flag = 0;

    public static void main(String[] args) {
        Question04 abc = new Question04();

        new Thread(new Runnable() {
            @Override
            public void run() {
                abc.printA();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                abc.printB();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                abc.printC();
            }
        }).start();
    }

    public synchronized void printA() {
        for (int i = 0; i < 5; i++) {
            while (0 != flag) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            flag = 1;
            System.out.print("A");
            notifyAll();
        }
    }

    public synchronized void printB() {
        for (int i = 0; i < 5; i++) {
            while (1 != flag) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            flag = 2;
            System.out.print("B");
            notifyAll();
        }
    }

    public synchronized void printC() {
        for (int i = 0; i < 5; i++) {
            while (2 != flag) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            flag = 0;
            System.out.print("C");
            notifyAll();
        }
    }
}
