package com.ww;

/**
 * @author xiaohua
 * @date 2021-3-31 17:03
 * @description 写两个线程，一个线程打印1-52，另一个线程打印A-Z，打印顺序是12A34B...5152Z
 */
public class Question03 {

    private boolean flag;

    private int count;

    public static void main(String[] args) {
        Question03 question03 = new Question03();

        new Thread(new Runnable() {
            @Override
            public void run() {
                question03.printNum();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                question03.printLetter();
            }
        }).start();
    }

    /**
     * 打印数字
     */
    public synchronized void printNum() {
        for (int i = 0; i < 26; i++) {
            while (flag) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            flag = !flag;
            System.out.print(++count);
            System.out.print(++count);
            notify();
        }
    }

    /**
     * 打印字符
     */
    public synchronized void printLetter() {
        for (int i = 0; i < 26; i++) {
            while (!flag) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            flag = !flag;
            System.out.print((char) (65 + i));
            notify();
        }
    }
}
