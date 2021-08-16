package com.ww.basic.uncaughtex;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xiaohua
 * @description TODO
 * @date 2021-8-15 7:49
 */
public class CaptureUncaughtException {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool(new HandlerThreadFactory());
        service.execute(new ExceptionThread2());
        service.shutdown();
    }
}
