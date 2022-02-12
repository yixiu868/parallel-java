package com.ww.application.multidownload;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author xiaohua
 * @description Java多线程下载网络文件，开启多个线程
 * @date 2021-9-17 17:10
 *  每一个线程下载的位置计算方式：
 *  开始位置，结束位置
 *
 *  有时候不一定能够整除，所以最后一个线程的结束位置应该是文件的末尾
 *
 *  步骤：
 *  1、本地创建一个大小跟服务器文件相同的临时文件；
 *  2、计算分配几个线程去下载服务器上的资源，知道每个线程下载文件的开始结束位置；
 *  3、开启三个线程，每个线程下载对应位置的文件；
 *  4、所有线程把数据下载完毕后，服务器上的资源也就被下载到了本地
 *
 *  参考链接：https://www.cnblogs.com/lr393993507/p/5455922.html
 */
public class MultiThreadDownload {

    public static String path = "https://t7.baidu.com/it/u=2204205512,3039153138&fm=193&f=GIF";

    public static int threadCount = 4;

    public static int runningThread = 4;

    public static long startTime;

    private static final String filePath = "D:/test/GIF.jfif";

    public static void main(String[] args) throws IOException {
        URL url = new URL(path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(5000);
        connection.setRequestMethod("GET");
        int code = connection.getResponseCode();
        if (200 == code) {
            int length = connection.getContentLength();
            System.out.println("文件总长度:" + length);
            // 创建临时文件
            RandomAccessFile raf = new RandomAccessFile(filePath, "rwd");
            raf.setLength(length);
            raf.close();

            int blockSize = length / threadCount;
            for (int threadId = 1; threadId <= threadCount; threadId++) {
                int startIndex = (threadId - 1) * blockSize;
                int endIndex = threadId * blockSize - 1;
                if (threadId == threadCount) {
                    endIndex = length;
                }
                System.out.println("线程" + threadId + "下载：" + startIndex + "--->" + endIndex);
                new DownloadThread(path, filePath, threadId, startIndex, endIndex).start();
            }
        } else {
            System.out.println("下载失败");
        }
    }
}
