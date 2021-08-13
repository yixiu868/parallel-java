package com.ww.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author xiaohua
 * @description Thread.stop()方法造成数据问题演示程序
 * @date 2021-8-13 13:54
 */
public class StopThreadUnsafe {

    public static User u = new User();

    public static class User {

        private int id;

        private String name;

        public User() {
            id = 0;
            name = "0";
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static class ChangeObjectThread extends Thread {

        volatile boolean stopme = false;

        /**
         * 既然使用stop()方法，会出现数据不一致风险，那么如何正确停止一个线程呢？
         * 实现方式就是这个方法
         */
        public void stopThread() {
            stopme = true;
        }

        @Override
        public void run() {
            while (true) {

                if (stopme) {
                    System.out.println("退出当前线程了！");
                    break;
                }

                synchronized (u) {
                    int v = (int) (System.currentTimeMillis() / 1000);
                    // 先设置id
                    u.setId(v);

                    // 模拟执行业务逻辑耗时
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // 再设置name
                    u.setName(String.valueOf(v));
                }

                Thread.yield();
            }
        }
    }

    public static class ReadObjectThread extends Thread {

        @Override
        public void run() {
            while (true) {
                synchronized (u) {
                    if (u.getId() != Integer.parseInt(u.getName())) {
                        System.out.println("id、name字段值不匹配");
                        System.out.println(u);
                    } else {
                        System.out.println("id、name字段值匹配");
                        System.out.println(u);
                    }
                }

                Thread.yield();
            }
        }
    }

    /**
     * 程序运行打印结果
     * id、name字段值不匹配
     * User{id=1628834667, name='1628834666'}
     * id、name字段值不匹配
     * User{id=1628834667, name='1628834666'}
     * id、name字段值不匹配
     * User{id=1628834667, name='1628834666'}
     * id、name字段值不匹配
     * User{id=1628834667, name='1628834666'}
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        new ReadObjectThread().start();

        while (true) {
            ChangeObjectThread thread = new ChangeObjectThread();
            thread.start();
            TimeUnit.MILLISECONDS.sleep(150);

            // 正确停止线程做法
            thread.stopThread();
            break;

            // 错误停止线程做法
//            thread.stop();
        }
    }
}
