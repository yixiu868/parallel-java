package com.ww.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 多路复用模型
 *
 * 期待的是，每次轮询值轮询有数据的Channel, 没有数据的就不管他，比如刚刚的例子，只有1000个活跃连接，
 * 那么每次就只轮询这1000个，其他的有读写的数据就轮询，没读写就不轮询！
 *
 * NIO底层在JDK1.4版本是用Linux内核函数select()或poll()来实现的，跟NioServer.java代码类似，selector每次都会轮询
 * 所有的socketChannel，看下哪个channel有读写事件，有的话就处理，没有就继续遍历，JDK1.5开始引入了epoll基于事件响应
 * 机制来优化NIO，首先我们会将我们的SocketChannel注册到对应的选择器上并选择关注的事件，后续操作系统会根据我们设置的感
 * 兴趣的事件将完成的事件SocketChannel放回到选择器中，等待用户的处理。
 *
 * 同步非阻塞IO痛点在于CPU总是在做很多无用的轮询，在这个模型里被解决了。这个模型从Selector中获取到的Channel全部是就绪
 * 的，也就是说每次轮询都不会做无用功。
 *
 * NIO整个调用流程就是Java调用了操作系统的内核函数来创建Socket，获取到Socket的文件描述符，再创建一个Selector对象，
 * 对应操作系统的Epoll描述符，将获取到的Socket连接的文件描述符的事件绑定到Selector对应的Epoll文件描述符上，进行事件
 * 的异步通知，这样就实现了使用一条线程，并且不需要太多的无效的遍历，将事件处理交给了操作系统内核(操作系统中断程序实现)，
 * 大大提高了效率。
 *
 * 参考链接
 * <a href="https://cloud.tencent.com/developer/article/1806388?from=article.detail.1857255">深入Hotspot源码与Linux内核理解NIO与Epoll</a>
 * <a href="https://cloud.tencent.com/developer/article/1808870?from=article.detail.1857255">深入Hotspot源码与Linux内核理解NIO与Epoll</a>
 *
 * epoll模型
 * epoll总共分为三个比较重要的函数：
 * ①epoll_create对应JDK NIO代码中的Selector.open()
 * ②epoll_ctl对应JDK NIO代码中的socketChannel.register(selector, xxx)
 * ③epoll_wait对应JDK NIO代码中的selector.select()
 *
 * @author wanggw
 * @Date 2022年03月08 16:10
 */
public class NioSelectorServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(9000));
        serverSocket.configureBlocking(false);
        // 打开Selector处理Channel，即创建epoll
        // 作用：
        // 在Linux的内核创建了Epoll实例，也就是创建了一个多路复用器
        Selector selector = Selector.open();
        // 把ServerSocketChannel注册到selector上，并且selector对客户端accept连接操作感兴趣
        // 作用：
        // 将channel注册到多路复用器上
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务启动成功");

        while (true) {
            // 阻塞等待需要处理的事件发生
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    // 如果是OP_ACCEPT事件，则进行连接获取和事件注册
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = server.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println("客户端连接成功");
                } else if (key.isReadable()) {
                    // 如果是OP_READ事件，则进行读取
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                    int len = socketChannel.read(byteBuffer);
                    if (len > 0) {
                        System.out.println("接收到消息:" + new String(byteBuffer.array()));
                    } else if (-1 == len) {
                        System.out.println("客户端断开连接");
                        socketChannel.close();
                    }
                }
                // 从事件集合中删除本次处理的key，防止下次select重复处理
                iterator.remove();
            }
        }
    }
}
