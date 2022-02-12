package com.ww.pattern.single.thread.impl01;

/**
 * @author xiaohua
 * @description 入口
 * @date 2021-10-12 9:07
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("测试开始...");
        Gate gate = new Gate();
        new UserThread(gate, "Alice", "Alaska").start();
        new UserThread(gate, "Bobby", "Brazil").start();
        new UserThread(gate, "Chris", "Canada").start();
    }
}
