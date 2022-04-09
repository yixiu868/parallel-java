# synchronized锁

## synchronized原理讲解

从JVM规范中可以看到Synchonized在JVM里的实现原理，JVM基于进入和退出Monitor对象来实现方法同步和代码块同步，但两者的实现细节不一样，代码块同步是使用monitorenter和monitorexit指令实现的，而方法同步是使用另外一种方式实现的，细节在JVM规范并没有详细说明，但是，方法的同步同样可以使用这两个指令来实现的。

`monitorenter指令是在编译后插入到同步代码块的开始位置，而monitorexit是插入到方法结束处和异常处，JVM要保证每个monitorenter必须有对应的monitorexit与之配对`。任何对象都有一个monitor与之关联，当且一个monitor被持有后，它将处于锁定状态。线程执行到monitorenter指令时，将会尝试获取对象所对应的monitor的所有权，即尝试获得对象的锁。



## 为什么synchronized成为重量级锁（JDK1.6）

JDK1.6之前是重量级锁，线程进入同步代码块/方法时，monitor对象就会把当前线程的id进行存储，设置Mark Word的monitor对象地址，并把阻塞的线程存储到monitor等待线程队列中。

加锁底层依赖操作系统的mutex相关指令，会有用户态和内核态之间的切换，性能损耗明显。

## synchronized锁升级

### Java对象头

Java对象大致可分为三个部分，对象头、实例变量和填充字节。

* 对象头是由`MarkWord和Klass Point（类型指针）`组成，其中Kclass Point是对象指向它的类元数据的指针，虚拟机通过这个指针来确定这个对象是哪个类的实例，`Mark Word用于存储对象自身的运行时数据`。如果对象是数组对象，那么对象头占用3个字宽，如果对象是非数组对象，那么对象头占用2个字宽。
* 实例变量存储是对象的属性信息，包括父类的属性信息，按照4字节对齐；
* 填充字符，因为`虚拟机要求对象字节必须是8字节的整数倍`，填充字符就是用于凑齐这个整数倍的。

![20210816113324](img\20210816113324.png)

`偏向锁和轻量级锁都是在Java6以后对锁机制进行优化时引进的`

### 重量级锁原理讲解

[Java~深度剖析synchronized与ObjectMonitor、volatile和内存屏障](https://blog.csdn.net/Shangxingya/article/details/111677184)

重量级锁对应的锁标志位是10，存储了指向重量级监视器锁的指针，在Hotspot中，对象的监视器锁对象由ObjectMonitor对象（C++实现），数据结构如下：

```c++
ObjectMonitor() {
    _header       = NULL;
    _count        = 0; //记录个数
    _waiters      = 0,
    _recursions   = 0;
    _object       = NULL;
    _owner        = NULL;
    _WaitSet      = NULL; //处于wait状态的线程，会被加入到_WaitSet
    _WaitSetLock  = 0 ;
    _Responsible  = NULL ;
    _succ         = NULL ;
    _cxq          = NULL ;
    FreeNext      = NULL ;
    _EntryList    = NULL ; //处于等待锁block状态的线程，会被加入到该列表
    _SpinFreq     = 0 ;
    _SpinClock    = 0 ;
    OwnerIsThread = 0 ;
  }
```

![20190403174421871](img\20190403174421871.jpg)

> * ObjectMonitor中有两个队列，\_WaitSet和_EntryList
> * 每个等待锁的线程都会被封装成ObjectWaiter对象
> * _owner指向持有ObjectMonitor对象的线程，也就是正在访问同步代码块的线程
> * 当多个线程同时访问一段同步代码时，首先会进入\_EntryList集合，当线程检查\_count为0，进入_Owner区域并把monitor中的owner变量设置为当前线程，同时monitor中的计数器count加1，此时也就意味着这个线程获取到了monitor对象，**当检查到owner不为null的时候就会执行owner指向的这个线程**。
> * 如果获取monitor对象失败，该线程则会进入阻塞状态，知道其他线程释放锁。
> * 若线程调用wait()方法，将释放当前持有的monitor，owner变量恢复为null，count自减1，同时该线程进入WaitSet集合中等待被唤醒。若当前线程执行完毕也将释放monitor并复位变量的值，以便其他线程进入获取monitor。

> 得出结论
>
> * 1、monitor对象存在于每个Java对象的对象头中，synchronized锁便是通过这种方式获取锁的，也是为什么Java中任意对象都可以作为锁的原因。
> * 2、synchronized是通过对象内部的一个叫做monitor来实现的，monitor本质又是依赖于底层操作系统的Mutex Lock（互斥锁）来实现的。
> * 3、synchronized是可重入的，所以不会自己把自己锁死，在monitor中，多次获取只是计数器count++即可，当释放的时候也只要计数器count减到0，即说明是无锁状态。
> * 4、synchronized锁一旦被一个线程是有，其他视图获取该锁的线程将被阻塞。操作系统实现阻塞，需要线程之间的切换，从用户态转换到内核态，成本高，状态转换需要时间相对较长，这也就是为什么说synchronized效率低的原因。

#### synchronized修改的代码块、方法如何获取monitor对象？

##### 同步代码块

synchronized同步代码块，在代码块开始的位置插入monitorentry指令，在同步结束的位置或者异常出现的位置插入monitorexit指令，JVM要保证`monitorentry和monitorexit`成对出现。

```java
public class SyncCodeBlock {
   public int i;
   public void syncTask(){
       synchronized (this){
           i++;
       }
   }
}
```

对同步代码块编译后的class字节码文件反编译

```java
public void syncTask();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=3, locals=3, args_size=1
         0: aload_0
         1: dup
         2: astore_1
         3: monitorenter  //注意此处，进入同步方法
         4: aload_0
         5: dup
         6: getfield      #2             // Field i:I
         9: iconst_1
        10: iadd
        11: putfield      #2            // Field i:I
        14: aload_1
        15: monitorexit   //注意此处，退出同步方法
        16: goto          24
        19: astore_2
        20: aload_1
        21: monitorexit //注意此处，退出同步方法
        22: aload_2
        23: athrow
        24: return
      Exception table:
      //省略其他字节码.......
```

##### 同步方法

synchronized同步方法不再是通过插入monitorentry和monitorexit指令实现，而是由方法调用指令来读取运行时常量池中的`ACC_SYNCHRONIZED标志隐式实现的`。

```java
public class SyncMethod {
   public int i;
   public synchronized void syncTask(){
           i++;
   }
}
```

对同步方法编译后的class字节码反编译

```java
public synchronized void syncTask();
    descriptor: ()V
    //方法标识ACC_PUBLIC代表public修饰，ACC_SYNCHRONIZED指明该方法为同步方法
    flags: ACC_PUBLIC, ACC_SYNCHRONIZED
    Code:
      stack=3, locals=1, args_size=1
         0: aload_0
         1: dup
         2: getfield      #2                  // Field i:I
         5: iconst_1
         6: iadd
         7: putfield      #2                  // Field i:I
        10: return
      LineNumberTable:
        line 12: 0
        line 13: 10
}
```

### 锁升级

锁的4种状态：无锁、偏向锁、轻量级锁、重量级锁

#### 偏向锁

`为什么要引入偏向锁`

大多时候是不存在锁竞争的，常常一个线程多次获得同一个锁，因此如果每次都要竞争锁会增大很多没有必要付出的代价，为了降低获取锁的代价，引入偏向锁。

##### 偏向锁的升级

当线程1访问代码块并获取锁的对象，会在`Java对象头和栈帧中记录偏向锁的threadID`，因为偏向锁不会主动释放锁，以后线程1再次获取锁的时候，需要`比较当前线程的threadID和Java对象头中threadID`是否一致，如果一致（还是线程1获取锁对象），则无需使用CAS加锁、解锁；

如果不一致（比如线程2要竞争锁对象，而偏向锁不会主动释放，因此还是存储线程1的threadID），那么需要查看Java对象头中记录的线程1是否还存活。如果没有存活，那么锁对象被重置为无锁状态，线程2可以竞争将其设置为偏向锁；`如果存活，那么立刻查找线程1的栈帧信息，如果还是需要继续持有这个锁对象，那么暂停当前线程1，撤销偏向锁，升级为轻量级锁`，如果线程1不再使用该锁对象，那么将锁对象状态设为无锁状态，重新偏向新的线程。

#### 轻量级锁

轻量级锁考虑的是竞争锁对象的线程不多，而且线程持有锁的时间也不长的场景。因为阻塞线程需要CPU从用户态转到内存态，代价较大，如果刚刚阻塞不久这个锁就释放了，更是有点得不偿失了，因此这个时候干脆不阻塞这个线程，让它自旋等待锁释放。

##### 轻量级锁什么时候升级为重量级锁

线程1获取轻量级锁时会`先把锁对象的对象头MarkWork复制一份到线程1的栈帧中`创建的用于存储锁记录（Lock Record）的空间，然后使用`CAS`把对象头中的内容`替换为线程1存储的锁记录的地址`；

如果在线程1复制对象头的同时（在线程1CAS之前），线程2也准备获取锁，复制了对象头到线程2的锁记录空间中，但是在线程2CAS的时候，发现线程1已经把对象头改变了，线程2的CAS失败，那么线程2就尝试使用自旋锁来等待线程1释放锁。

但是如果自旋的时间太长也不行，因为自旋是需要消耗CPU的，需要设置自旋的次数，默认为10，如果自旋次数到了线程1还没有释放锁，或者线程1还在执行，线程2还在自旋等待，这时又有一个线程3过来竞争这个锁对象，那么这个时候轻量级锁就会膨胀为重量级锁。重量级锁把除了拥有锁的线程都阻塞，防止CPU空转。

`锁可以升级不可以降级，但是偏向锁状态可以被重置为无锁状态`

[深入研究synchronized锁升级](https://www.cnblogs.com/trunks2008/p/14646610.html#top)

![1c92f8c9bd79aea7f22846f976373dd2_640_wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1](img\1c92f8c9bd79aea7f22846f976373dd2_640_wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1.webp)
