package com.ww.pattern.single.thread.impl02;

/**
 * @author xiaohua
 * @description Single Thread模式共享资源类
 * @date 2021-10-12 9:01
 */
public class Gate {

    private int counter = 0;

    private String name = "Nobody";

    private String address = "Nowhere";

    public synchronized void pass(String name, String address) {
        this.counter++;
        this.name = name;

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.address = address;
        check();
    }

    public synchronized String toString() {
        return "No." + counter + ": " + name + ", " + address;
    }

    private void check() {
        if (name.charAt(0) != address.charAt(0)) {
            System.out.println("**** BROKEN ****" + toString());
        }
    }
}
