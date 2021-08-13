package com.ww.basic.productconsumer;

/**
 * @author xiaohua
 * @description 生产者
 * @date 2021-8-13 10:57
 */
public class Producer extends Thread {

    // 每次生产数量
    private int num;

    // 仓库
    public AbstractStorage abstractStorage;

    public Producer(AbstractStorage abstractStorage) {
        this.abstractStorage = abstractStorage;
    }

    @Override
    public void run() {
        produce(num);
    }

    /**
     * 加库存
     * @param num
     */
    private void produce(int num) {
        abstractStorage.produce(num);
    }

    public void setNum(int num) {
        this.num = num;
    }
}
