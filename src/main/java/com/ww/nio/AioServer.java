package com.ww.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author wanggw
 * @Date 2022年03月08 16:38
 */
public class AioServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        final AsynchronousServerSocketChannel severChannel = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(9000));
        severChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            @Override
            public void completed(AsynchronousSocketChannel socketChannel, Object attachment) {
                try {
                    System.out.println("2--" + Thread.currentThread().getName());
                    severChannel.accept(attachment, this);
                    System.out.println(socketChannel.getRemoteAddress());
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    socketChannel.read(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer attachment) {
                            System.out.println("3--" + Thread.currentThread().getName());
                            byteBuffer.flip();
                            System.out.println(new String(byteBuffer.array(), 0, result));
                            socketChannel.write(ByteBuffer.wrap("HelloClient".getBytes()));
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            exc.printStackTrace();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();
            }
        });

        System.out.println("1--" + Thread.currentThread().getName());
        Thread.sleep(Integer.MAX_VALUE);
    }
}
