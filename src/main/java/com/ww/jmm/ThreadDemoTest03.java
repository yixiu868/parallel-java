package com.ww.jmm;

/**
 * @author xiaohua
 * @description 测试内存可见性
 * @date 2021-8-24 11:19
 */
public class ThreadDemoTest03 {

    public static void main(String[] args) {
        ThreadDemo02 demo = new ThreadDemo02();
        demo.start();

        for (;;) {
            synchronized (demo) {
                if (demo.isFlag()) {
                    System.out.println("你猜你能看到吗");
                }
            }
        }
    }
}
