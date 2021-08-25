package com.ww.jmm;

/**
 * @author xiaohua
 * @description 测试内存可见性
 * @date 2021-8-24 11:19
 */
public class ThreadDemoTest02 {

    public static void main(String[] args) {
        ThreadDemo demo = new ThreadDemo();
        demo.start();

        for (;;) {
            // 加了同步块后，就可以看到打印输出了，又是为什么？
            // ---------------------------------------
            // 详细解答：
            // 因为某一个线程进入synchronized代码块前后，线程会获得锁，清空工作内存！！！
            // 从主内存拷贝共享变量最新的值到工作内存，执行代码，将修改后的副本的值刷新回主内存！！！释放锁。
            synchronized (demo) {
                if (demo.isFlag()) {
                    System.out.println("你猜你能看到吗");
                }
            }
        }
    }
}
