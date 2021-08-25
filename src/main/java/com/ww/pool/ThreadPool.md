# 线程池

## ThreadPoolExecutor

### 构造方法重要参数分析

* `corePoolSize` 核心线程数定义了最小可以同时运行的线程数量。
* `maximumPoolSize`当队列中存放的任务**达到队列容量的时候**，当前可以同时运行的线程数量变为最大线程数。
* `workQueue`当新任务来的时候会先判断当前运行的线程数量**是否达到了核心线程数**，如果达到了，新任务就会被放入队列中。

`在银盛面试时候答这个问题就搞了，要记住了，达到核心线程数据新任务就放入队列中，队列放满后才会增加线程数到最大线程数，不要说反！！！`

### 其他常见参数

* `keepAliveTime`当线程池中的**线程数大于**`corePoolSize`时，这个时候**又没有新任务提交**，核心线程外的线程也不会立即被销毁，而是先等待，知道等待的时间超过了`keepAliveTime`，才会被回收掉。
* `unit` `keepAliveTime`参数的时间单位。
* `threadFactory`executor创建新线程的时候会用到。
* `handler`饱和策略。

### ThreadPoolExecutor饱和策略

#### 定义

如果当前同时运行的**线程数量达到最大线程数量**并且**队列也被放满了**任务时。

#### 策略

* `ThreadPoolExecutor.AbortPolicy`抛出RejectedExecutionException来**拒绝新任务**的处理。
* `ThreadPoolExecutor.CallerRunsPolicy`调用执行自己的线程运行任务。这个策略喜欢增加队列容量，如果你的任务可以承受延迟并且不能丢弃任何一个任务请求的话，可以考虑该策略。
* `ThreadpoolExecutor.DiscardPolicy`不处理新任务，直接丢弃掉。
* `ThreadPoolExecutor.DiscardOldestPolicy`将丢弃最早未处理的任务请求。



![640](img\640.webp)

