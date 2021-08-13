package com.ww.basic.productconsumer;

import java.util.LinkedList;

/**
 * @author xiaohua
 * @description 具体仓库
 * @date 2021-8-13 11:03
 */
public class ConcreteStorage implements AbstractStorage {

    // 仓库最大容量
    private static final int MAX_SIZE = 100;

    // 仓库存储结构
    private LinkedList list = new LinkedList();

    @Override
    public void consume(int num) {
        synchronized (list) {
            // 库存不够消费数量
            while (num > list.size()) {
                System.out.println("[准备消费数量]" + num + "\t[库存量]" + list.size() + "\t无法满足消费，需要加库存");

                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i < num; i++) {
                list.remove();
            }

            System.out.println("[已消费]" + num + "个\t[库存量]" + list.size());
            list.notifyAll();
        }
    }

    /**
     * 生产
     * @param num
     */
    @Override
    public void produce(int num) {
        synchronized (list) {
            while (list.size() + num > MAX_SIZE) {
                System.out.println("[准备生产数量]" + num + "\t[库存量]" + list.size() + "\t暂时无法进行加库存");

                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // 满足条件，进行生产，加库存
            for (int i = 0; i < num; i++) {
                list.add(new Object());
            }

            System.out.println("[已生产产品]" + num + "个\t[库存量]" + list.size());
            list.notifyAll();
        }
    }
}
