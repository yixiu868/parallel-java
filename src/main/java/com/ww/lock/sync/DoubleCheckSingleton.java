package com.ww.lock.sync;

/**
 * @author xiaohua
 * @description 双重检验锁单例
 * @date 2021-8-25 9:02
 */
public class DoubleCheckSingleton {

    // 实例化对象需要采用volatile关键字修饰
    // DoubleCheckSingleton uniqueInstance = new DoubleCheckSingleton()分为3步
    // 1、为uniqueInstance分配内存空间
    // 2、初始化uniqueInstance
    // 3、将uniqueInstance指向分配的内存地址
    // 由于JVM具有指令重排的特性，执行顺序可能变成 1->3->2，在单线程下是没有问题的，在多线程下会出现问题
    private volatile static DoubleCheckSingleton uniqueInstance;

    private DoubleCheckSingleton() {}

    public static DoubleCheckSingleton getInstance() {
        // 先判断对象是否已经实例化，如果没有实例化才进入同步块
        if (null == uniqueInstance) {
            synchronized (DoubleCheckSingleton.class) {
                // 再次检查对象是否实例化
                if (null == uniqueInstance) {
                    uniqueInstance = new DoubleCheckSingleton();
                }
            }
        }

        return uniqueInstance;
    }
}
