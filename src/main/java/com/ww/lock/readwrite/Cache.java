package com.ww.lock.readwrite;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁实现本地缓存
 * @author wanggw
 * @Date 2022年02月27 11:47
 */
public class Cache {

    private static final Map<String, Object> map = new HashMap<>();

    private static final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    private static final Lock rLock = rwl.readLock();

    private static final Lock wLock = rwl.writeLock();

    public static final Object get(String key) {
        rLock.lock();
        try {
            return map.get(key);
        } finally {
            rLock.unlock();
        }
    }

    public static final Object put(String key, Object value) {
        wLock.lock();
        try {
            return map.put(key, value);
        } finally {
            wLock.unlock();
        }
    }

    public static final void clear() {
        wLock.lock();
        try {
            map.clear();
        } finally {
            wLock.unlock();
        }
    }
}
