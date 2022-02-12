# IO

## 同步、异步，阻塞、非阻塞IO

参考链接

[迄今为止把同步/异步/阻塞/非阻塞/BIO/NIO/AIO讲的这么清楚的好文章](https://mp.weixin.qq.com/s/EVequWGVMWV5Ki2llFzdHg)

### 同步IO和同步阻塞IO

同步IO是指发起IO请求后，必须拿到IO的数据才可以继续执行。

表现形式又分为两种：

* 1、在等待数据的过程中，和拷贝数据的过程中，线程都在阻塞，这就是同步阻塞IO。
* 2、在等待数据的过程中，线程采用死循环轮询，在拷贝数据的过程中，线程在阻塞，这其实也还是同步阻塞IO。

### 异步IO和异步阻塞、非阻塞IO

异步IO是指发起IO请求后，不用拿到IO的数据就可以继续执行。

分为两种情况：

* 1、在等待数据的过程中，用户线程在继续执行，在拷贝数据的过程中，线程在阻塞，这就是异步阻塞IO。
* 2、在等待数据的过程中，和在拷贝数据的过程中，用户线程都在继续执行，这就是异步非阻塞IO。

## IO多路复用

参考链接

[这次答应我，一举拿下 I/O 多路复用](https://mp.weixin.qq.com/s?__biz=MzUxODAzNDg4NQ==&mid=2247489558&idx=1&sn=7a96604032d28b8843ca89cb8c129154&scene=21#wechat_redirect)

[原来 8 张图，就能学废 Reactor 和 Proactor](https://mp.weixin.qq.com/s/px6-YnPEUCEqYIp_YHhDzg)

### Reactor模式

来了一个事件，Reactor就有相对应的反应/响应；

IO多路复用监听事件，收到事件后，根据事件类型分配给某个进程、线程；

#### 单Reactor单进程/线程

![562e98fe28cfde6de9fc20c56980cc3d_640_wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1](img\562e98fe28cfde6de9fc20c56980cc3d_640_wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1.webp)

- Reactor 对象的作用是监听和分发事件；
- Acceptor 对象的作用是获取连接；
- Handler 对象的作用是处理业务；

`处理过程`

- Reactor 对象通过 select （IO 多路复用接口） 监听事件，收到事件后通过 dispatch 进行分发，具体分发给 Acceptor 对象还是 Handler 对象，还要看收到的事件类型；
- 如果是连接建立的事件，则交由 Acceptor 对象进行处理，Acceptor 对象会通过 accept 方法 获取连接，并创建一个 Handler 对象来处理后续的响应事件；
- 如果不是连接建立事件， 则交由当前连接对应的 Handler 对象来进行响应；
- Handler 对象通过 read -> 业务处理 -> send 的流程来完成完整的业务流程。

#### 单Reactor多线程/多进程

![dd85dd259cb6bf168f9428448c9b38a8_640_wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1](img\dd85dd259cb6bf168f9428448c9b38a8_640_wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1.webp)

`处理过程`

- Reactor 对象通过 select （IO 多路复用接口） 监听事件，收到事件后通过 dispatch 进行分发，具体分发给 Acceptor 对象还是 Handler 对象，还要看收到的事件类型；
- 如果是连接建立的事件，则交由 Acceptor 对象进行处理，Acceptor 对象会通过 accept 方法 获取连接，并创建一个 Handler 对象来处理后续的响应事件；
- 如果不是连接建立事件， 则交由当前连接对应的 Handler 对象来进行响应；

`与单进程、单线程区别`

- Handler 对象不再负责业务处理，只负责数据的接收和发送，Handler 对象通过 read 读取到数据后，会将数据发给子线程里的 Processor 对象进行业务处理；
- 子线程里的 Processor 对象就进行业务处理，处理完后，将结果发给主线程中的 Handler 对象，接着由 Handler 通过 send 方法将响应结果发送给 client；

#### 多Reactor多线程/多进程

![82a33915f4b3ab332cedf24a0e50fbb9_640_wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1](img\82a33915f4b3ab332cedf24a0e50fbb9_640_wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1.webp)

大名鼎鼎的两个开源软件 Netty 和 Memcache 都采用了「多 Reactor 多线程」的方案。

方案详细说明如下：

* 1、主线程中的MainReactor对象通过select监控连接建立事件，收到事件后通过Acceptor对象中的accept获取连接，将新的连接分配给某个子线程；
* 2、子线程中的SubReactor对象将MainReactor对象分配的连接加入select继续进行监听，并创建一个Handler用于处理连接的响应事件；
* 3、如果有新的事件发生时，SubReactor对象会调用当前连接对应的Handler对象来进行响应；
* Handler对象通过read->业务处理->send的流程来完成完整的业务流程。