package com.example.zookeeper.lock.sql;

import com.example.zookeeper.lock.Lock;

/**
 * @author yujiale
 */
public abstract class AbstractLock implements Lock {

    @Override
    public void lock() {
        if (tryLock()) {
            System.out.println("尝试获取锁");
        } else {
            waitLock();
            lock();
        }

    }


    @Override
    public void unlock() {

    }


    private void waitLock() {
    }

    private boolean tryLock() {
        return false;
    }


}
