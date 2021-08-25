package com.ww.jmm;

import java.util.concurrent.TimeUnit;

/**
 * @author xiaohua
 * @description 测试内存可见性
 * @date 2021-8-24 11:18
 */
public class ThreadDemo02 extends Thread {

    private boolean flag = false;

    public boolean isFlag() {
        return flag;
    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
        System.out.println("flag = " + flag);
    }
}
