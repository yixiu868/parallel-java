package com.ww.application.multidownload;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author xiaohua
 * @description 下载子线程
 * @date 2021-9-17 17:17
 */
public class DownloadThread extends Thread {

    // 线程ID
    private int threadId;

    // 下载开始位置
    private int startIndex;

    // 下载结束位置
    private int endIndex;

    private String urlPath;

    private String filePath;

    public DownloadThread(String urlPath, String filePath, int threadId, int startIndex, int endIndex) {
        this.urlPath = urlPath;
        this.filePath = filePath;
        this.threadId = threadId;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public void run() {
        try {
            long startTime = System.currentTimeMillis();
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Range", "bytes=" + startIndex + "-" + endIndex);
            int code = connection.getResponseCode();
            System.out.println("code:" + code);
            InputStream inputStream = connection.getInputStream();
            RandomAccessFile file = new RandomAccessFile(filePath, "rwd");
            file.seek(startIndex);

            int len = 0;
            byte[] buffer = new byte[1024];
            while (-1 != (len = inputStream.read(buffer))) {
                file.write(buffer, 0, len);
            }

            inputStream.close();
            file.close();
            System.out.println("线程" + threadId + "下载完毕");
            System.out.println("耗时:" + (System.currentTimeMillis() - startTime));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
