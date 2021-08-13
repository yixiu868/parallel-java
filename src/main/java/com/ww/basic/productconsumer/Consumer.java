package com.ww.basic.productconsumer;

/**
 * @author xiaohua
 * @description 消费者
 * @date 2021-8-13 11:00
 */
public class Consumer extends Thread {

    // 每次消费产品数量
    private int num;

    // 仓库
    private AbstractStorage abstractStorage;

    public Consumer(AbstractStorage abstractStorage) {
        this.abstractStorage = abstractStorage;
    }

    @Override
    public void run() {
        consume(num);
    }

    private void consume(int num) {
        abstractStorage.consume(num);
    }

    public void setNum(int num) {
        this.num = num;
    }
}
