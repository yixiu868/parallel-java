package com.ww.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * @author wanggw
 * @Date 2022年03月03 20:02
 */
public class ForkJoinPoolDemo {

    class SendMsgTask extends RecursiveAction {

        /**
         * 
         */
        private static final long serialVersionUID = 8151874009291424607L;

        private final int THRESHOLD = 10;

        private int start;

        private int end;

        private List<String> list;

        public SendMsgTask(int start, int end, List<String> list) {
            this.start = start;
            this.end = end;
            this.list = list;
        }

        @Override
        protected void compute() {
            if ((end - start) <= THRESHOLD) {
                for (int i = start; i < end; i++) {
                    System.out.println(Thread.currentThread().getName() + ":" + list.get(i));
                }
            } else {
                int middle = (start + end) / 2;
                invokeAll(new SendMsgTask(start, middle, list), new SendMsgTask(middle, end, list));
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 123; i++) {
            list.add(String.valueOf(i + 1));
        }

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.submit(new ForkJoinPoolDemo().new SendMsgTask(0, list.size(), list));
        forkJoinPool.awaitTermination(10, TimeUnit.SECONDS);
        forkJoinPool.shutdown();
    }
}
