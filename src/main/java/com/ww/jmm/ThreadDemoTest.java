package com.ww.jmm;

/**
 * @author xiaohua
 * @description 测试内存可见性
 * @date 2021-8-24 11:19
 */
public class ThreadDemoTest {

    public static void main(String[] args) {
        ThreadDemo demo = new ThreadDemo();
        demo.start();

        for (;;) {
            if (demo.isFlag()) {
                // 答案是你根本看不到打印输出，猜猜为什么？
                System.out.println("你猜你能看到吗");
            }
        }
    }
}
