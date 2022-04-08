package com.ww.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 使用了一条线程处理了所有的连接以及读写操作，但是假设我们有10w连接，活跃连接（经常read/write）只有1000，
 * 但是我们这个线程需要每次要轮询10w条数据处理，极大的消耗了CPU！
 * @author wanggw
 * @Date 2022年03月08 15:59
 */
public class NioServer {

    static List<SocketChannel> channelList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(9000));
        // 设置为非阻塞
        serverSocket.configureBlocking(false);
        System.out.println("服务启动成功");

        while (true) {
            SocketChannel socketChannel = serverSocket.accept();
            if (null != socketChannel) {
                System.out.println("连接成功");
                // 设置为非阻塞
                socketChannel.configureBlocking(false);
                // 保存客户端连接在List中
                channelList.add(socketChannel);
            }

            Iterator<SocketChannel> iterator = channelList.iterator();
            while (iterator.hasNext()) {
                SocketChannel sc = iterator.next();
                ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                // 非阻塞模式read方法不会阻塞，否则会阻塞
                int len = sc.read(byteBuffer);
                if (len > 0) {
                    System.out.println("接收到消息:" + new String(byteBuffer.array()));
                } else if (-1 == len) {
                    iterator.remove();
                    System.out.println("客户端断开连接");
                }
            }
        }
    }
}
