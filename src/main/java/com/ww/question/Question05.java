package com.ww.question;

/**
 * @author xiaohua
 * @date 2021-3-31 17:20
 * @description 编写10个线程，第一个线程从1加到10，第二个线程从11加到20...第十个线程从91加到100，最后再把10个线程结果相加
 * 后边有时间考虑使用使用callable重新实现，可以避免使用join等待返回结果（2021年9月25日16:09:31）
 */
public class Question05 {

    public static void main(String[] args) {
        int result = 0;

        for (int i = 0; i < 10; i++) {
            SumThread sumThread = new SumThread(i);
            sumThread.start();

            try {
            	// 这里还是很关键的
                sumThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            result = result + sumThread.sum;
        }

        System.out.println("result " + result);
    }

    static class SumThread extends Thread {

        int forcnt = 0;
        int sum = 0;

        SumThread(int forcnt) {
            this.forcnt = forcnt;
        }

        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                sum += i + forcnt * 10;
            }
            System.out.println(getName() + " " + sum);
        }
    }
}
