# AQS

## 原理图

![640](img\640.webp)

AQS使用一个int成员变量来表示同步状态，通过内置的FIFO队列来完成获取资源线程的排队工作。AQS使用CAS对该同步状态进行原子操作实现对其值的修改。

```java
private volatile int state; // 共享变量，使用volatile修饰保证线程可见性
```

## 资源共享方式

* `Exclusive（独占）`只有一个线程能执行，如ReentrantLock，又分为公平锁和非公平锁：
  * 公平锁：按照线程在队列中的排队顺序，先到者先拿到锁；
  * 非公平锁：当线程要获取锁时，无视队列顺序直接去抢，谁抢到就是谁的
* `Share（共享）`多个线程可同时执行，如Semaphore、CountDownLatch，CyclicBarrier、ReadWriteLock。

## AQS模板方法

```java
isHeldExclusively(); // 该线程是否正在独占资源，只有用到condition才需要去实现它
tryAcquire(int); // 独占方式。尝试获取资源，成功则返回true，失败则返回false
tryRelease(int); // 独占方式，尝试释放资源，成功返回true，失败返回false
tryAcquireShared(int); // 共享方式，尝试获取资源。负数表示失败，0表示成功，但没有剩余可用资源；正数表示成功，且有剩余资源
tryReleaseShared(int); // 共享方式，尝试释放资源，成功返回true，失败返回false
```

