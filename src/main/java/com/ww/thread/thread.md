# 多线程基础

## 状态

![20210813125956](img\20210813125956.png)

* NEW：表示刚刚创建的线程，这种线程还没开始执行start()，等到执行start()方法，才表示线程开始执行；

* RUNNABLE：当线程执行时，处于RUNNABLE状态，表示线程所需的一切资源都已经准备好了。
* BLOCKED：如果线程在执行的过程中遇到了synchronized同步块，就会进入BLOCKED阻塞状态，这时线程就会暂停执行，知道获得请求的锁；
* WAITING：等待状态，WAITING会进入一个无时间限制的等待；
* TIMED_WAITING：等待状态，进行一个有时限的等待；
* TERMINATED：当线程执行完毕后，则进入TERMINATED状态，表示结束；

### 线程start()方法与run()方法区别

不要用run()来开启新线程。它只会在当前线程中，串行执行run()中的代码；

### 如何终止线程（面试被问）

* 为什么不推荐stop()？

  原因是stop()方法太过于暴力，强行把执行到一半的线程终止，可能会引起一些数据不一致的问题。

  Thread.stop()方法在结束线程时，会直接终止线程，并且会立即释放这个线程所持有的锁。而这些锁恰恰是用来维持对象一致性的。如果此时，写线程写入数据正写到一半，并强行终止，那么对象就会被写坏，同时，由于锁已经被释放，另外一个等待该锁的读线程就顺理成章的读到这个不一致的对象，悲剧就此发生。

  ![20210813134404](img\20210813134404.png)

### 线程中断

线程中断不会使线程立即退出，而是给线程发送一个通知，告知目标线程，有人希望你退出啦！至于目标线程接到通知后如何处理，则完全由目标线程自行决定。这点很重要，如果中断后，线程立即无条件退出，又会遇到stop()方法的老问题。

```java
public void Thread.interrupt(); // 中断线程，通知目标线程中断，设置中断标志位
public boolean Thread.isInterrupted(); // 判断是否被中断
public static boolean Thread.interrupted(); // 判断是否被中断，并清除当前中断状态
```

### 挂起(suspend)和继续执行(resume)线程

**早已被标注为废弃方法，不推荐使用**

`不推荐使用suspend()去挂起线程的原因：suspend()在导致线程暂停的同时，并不会去释放任何锁资源。此时，其他任何线程想要访问被它暂用的锁时，都会被牵连，导致无法正常继续运行。直到对应的线程上进行了resume()操作，被挂起的线程才能继续，从而其他所有阻塞的相关锁上的线程也可以继续执行。但是，如果resume()操作意外的在suspend()前被执行了，那么被挂起的线程可能很难有机会被继续执行。并且，更为严重的是，它所占用的锁不会被释放，因此可能会导致整个系统工作不正常。而且，对于被挂起的线程，从它的线程状态上看，居然还是Runnable,这也会严重影响我们对系统当前状态的判断`