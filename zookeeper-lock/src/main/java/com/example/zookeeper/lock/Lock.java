package com.example.zookeeper.lock;

/**
 * @author yujiale
 */
public interface Lock {
    /**
     * 加锁
     */
    void lock();

    /**
     * 释放锁
     */
    void unLock();

    void unlock();
}
