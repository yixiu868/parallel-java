package com.ww.actual.project.queue;

public interface Queue<E> {

    /**
     * 放入队列
     * @param x
     * @throws InterruptedException
     */
    void put(E x) throws InterruptedException;

    /**
     * 获取队列的第一个元素，并且出队
     * @return
     * @throws InterruptedException
     */
    E take() throws InterruptedException;

    /**
     * 获取队列的第一个元素，不出队
     * @return
     */
    E peek();

    /**
     * 队列的第一个元素出队
     */
    void poll();

    /**
     * 队列大小
     * @return
     */
    int size();

    /**
     * 队列是否包含
     * @param x
     * @return
     */
    boolean contains(E x);

    /**
     * 队列剩余位置个数
     * @return
     */
    int remainingCapacity();

    /**
     * 清除队列
     */
    void clear();
}
