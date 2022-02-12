package com.ww.pattern.single.thread.impl01;

/**
 * @author xiaohua
 * @description 共享资源使用者
 * @date 2021-10-12 9:05
 */
public class UserThread extends Thread {

    private final Gate gate;

    private final String myname;

    private final String myaddress;

    public UserThread(Gate gate, String myname, String myaddress) {
        this.gate = gate;
        this.myname = myname;
        this.myaddress = myaddress;
    }

    @Override
    public void run() {
        System.out.println(myname + " BEGIN");
        while (true) {
            gate.pass(myname, myaddress);
        }
    }
}
