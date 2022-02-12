# CountDownLatch和CyclicBarrier

[CountDownLatch和CyclicBarrier](https://mp.weixin.qq.com/s?__biz=MzU4NzA3MTc5Mg==&mid=2247484363&idx=1&sn=743dcdfb84f83cfc38882407f87c7c6d&chksm=fdf0eb94ca87628296d86d16769f25e10acd052bcd78f4a4608f4218e4948aff610b04a41f60&token=960279204&lang=zh_CN&scene=21#wechat_redirect)

该链接除了讲解本次知识点，还讲解了面试技巧【面试官想要的是什么】，这个是很值得学习的事情。

```java
-- 面试官
    即使我想考察CountDownLatch和CyclicBarrier的知识，但是过程也是很重要的。我会看你在这个过程中【如何思考】以及【如何沟通】
```

## CountDownlatch

CountDownLatch基于AQS实现，会构造CountDownLatch的入参传递至state，countDown()就是在利用CAS将state减-1，await()实际就是让头节点一直在等待state为0时，释放所有等待的线程。

## CyclicBarrier

CyclicBarrier则利用ReentrantLock和Condition，自身为了count和parties变量，每次调用await将count-1，并将线程加入到condition队列上。等到count为0时，则将condition队列的节点移交至AQS队列，并全部释放。