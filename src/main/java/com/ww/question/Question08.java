package com.ww.question;

/**
 * @author xiaohua
 * @date 2021-3-31 19:57
 * @description 交替打印两个数组
 */
public class Question08 {

    int[] arr1 = new int[] { 1, 3, 5, 7, 9 };

    int[] arr2 = new int[] { 2, 4, 6, 8, 10 };

    boolean flag;

    public static void main(String[] args) {
        Question08 question08 = new Question08();

        new Thread(new Runnable() {
            @Override
            public void run() {
                question08.print1();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                question08.print2();
            }
        }).start();
    }

    public synchronized void print1() {
        for (int i = 0; i < arr1.length; i++) {
            while (flag) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            flag = !flag;
            System.out.print(arr1[i] + " ");
            notifyAll();
        }
    }

    public synchronized void print2() {
        for (int i = 0; i < arr2.length; i++) {
            while (!flag) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            flag = !flag;
            System.out.print(arr2[i] + " ");
            notifyAll();
        }
    }
}
