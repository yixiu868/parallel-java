# LongAdder

## AtomicLong缺点
AtomicLong做累加的时候实际上就是多个线程操作同一个目标资源。
高并发时，只有一个线程是执行成功的，其他线程都会失败，会不断自旋（重试），自旋会成为瓶颈。

## LongAdder实现
LongAdder的思想就是把要操作的目标资源【分散】到数组Cell中
每个线程对自己的Cell变量的value进行原子操作，大大降低了失败的次数
所以在高并发场景下，推荐使用LongAdder。
