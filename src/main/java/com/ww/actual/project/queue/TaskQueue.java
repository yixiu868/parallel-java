package com.ww.actual.project.queue;

import com.ww.actual.project.model.CheckBillTaskDto;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 任务队列
 */
public class TaskQueue implements Queue<CheckBillTaskDto> {

    /** 队列容量 */
    public int capacity = 5;

    /** 队列 */
    public BlockingQueue<CheckBillTaskDto> queue;

    /** 暂停任务 */
    public Map<String, Date> suspendTaskMap;

    /** 暂停分钟数，默认5分钟 */
    public int suspendMin = 5;

    /** 运行中任务set */
    public Set<String> runningTaskSet;

    /** 运行中任务最大数 */
    public int maxRunningTaskNum;

    public TaskQueue() {
        this.queue = new ArrayBlockingQueue<>(capacity);
        this.suspendTaskMap = new HashMap<>();
        this.runningTaskSet = new HashSet<>();
        this.maxRunningTaskNum = capacity;
    }

    @Override
    public void put(CheckBillTaskDto x) throws InterruptedException {

    }

    @Override
    public CheckBillTaskDto take() throws InterruptedException {
        return null;
    }

    @Override
    public CheckBillTaskDto peek() {
        return null;
    }

    @Override
    public void poll() {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean contains(CheckBillTaskDto x) {
        return false;
    }

    @Override
    public int remainingCapacity() {
        return 0;
    }

    @Override
    public void clear() {

    }
}
