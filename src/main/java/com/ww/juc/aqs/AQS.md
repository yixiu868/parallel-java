# AQS

## 基本介绍

AQS是lock的一个底层【框架】，内部实现的关键是维护了一个先进先出的队列以及`state状态变量`。

先进先出队列存储的载体叫做Node节点，该节点标识着当前的`状态值`，是否`独占`还是`共享模式`，`前驱`和`后继`节点。

### 总体流程

会把需要等待的线程以Node形式放到这个先进先出的队列上，state变量则表示为当前锁的状态。

`ReentrantLock`、`ReentrantReadWriteLock`、`CountDownLatch`、`Semaphore`都是基于AQS实现的。

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
* `Share（共享）`多个线程可同时执行，如Semaphore、CountDownLatch，CyclicBarrier、ReentrantReadWriteLock。

## AQS模板方法

```java
isHeldExclusively(); // 该线程是否正在独占资源，只有用到condition才需要去实现它
tryAcquire(int); // 独占方式。尝试获取资源，成功则返回true，失败则返回false
tryRelease(int); // 独占方式，尝试释放资源，成功返回true，失败返回false
tryAcquireShared(int); // 共享方式，尝试获取资源。负数表示失败，0表示成功，但没有剩余可用资源；正数表示成功，且有剩余资源
tryReleaseShared(int); // 共享方式，尝试释放资源，成功返回true，失败返回false
```

## ReentrantLock

### 获取锁过程

非公平锁为例

* 1）CAS尝试获取锁，获取成功则可以执行同步带吗；
* 2）CAS获取失败，则调用`acquire`方法，`acquire`方法实际上就是`AQS`的模板方法。然后调用`ReentrantLock.tryAcquire()`方法；
* 3）`tryAcquire`方法实际上会判断当前的`state`是否为0，等于0说明没有线程持有锁，则又尝试`CAS`直接获取锁。如果获取成功，则可以执行同步代码；
* 4）如果`CAS`获取失败，那么判断当前线程是否持有锁，如果持有锁，就更新`state`值（+1），获取到锁（处理可重入逻辑）。
* 5）`CAS`失败并且`非重入`的情况下，则回到`tryAcquire`方法执行【入队列】操作；
* 6）将节点加入队列之后，会判断【前驱节点】是不是`头节点`，如果是头节点又会CAS尝试获取锁。
* 7）如果【前驱节点】是头节点，并且获取得到锁，则把当前节点设置为头节点，并且把前驱节点置空（实际上原有的头节点已经释放锁了）。
* 8）没有获取到锁，则判断`前驱节点`的状态是否为`SIGNAL`，如果不是，则找到合适的前驱节点，并使用CAS将状态设置为`SIGNAL`
* 9）最后调用`park`将当前线程挂起；

### 释放锁过程

* 1）外界调用`unlock`方法时，实际上会调用AQS的`release`方法，而`release`方法又会调用`ReentrantLock.tryRelease()`方法。
* 2）tryRelease会把state一直减（锁重入state>1），直至到0，说明当前线程已经把锁释放了。
* 3）随后从队尾往前找节点状态需要<0，并离头节点最近的节点进行唤醒。唤醒之后，被唤醒的线程则尝试使用CAS获取锁，假设获取锁得到锁，则把头节点给干掉，把自己设置为头节点。

#### 解释

Node节点的状态有4种，分别是CANCELLED(1)、SIGNAL(-1)、CONDITION(-2)、PROPAGATE(-3)和0

在ReentrantLock解锁的时候，会判断节点的状态是否小于0，小于等于0才说明需要被唤醒。

#### 公平锁

公平锁在获取锁的时候，不会直接尝试CAS获取锁，只有当队列没有节点而且state状态为0才会直接获取锁，不然就会乖乖的加入到队列中。

