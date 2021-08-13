package com.ww.basic.productconsumer;

/**
 * @author xiaohua
 * @description 抽象仓库类
 * @date 2021-8-13 10:56
 */
public interface AbstractStorage {

    void consume(int num);

    void produce(int num);
}
