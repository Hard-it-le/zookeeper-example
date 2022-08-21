package com.zk.api;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @program: zookeeper-demo
 * @author: yjl
 * @created: 2022/04/24
 */
public class DeleteNode implements Watcher {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    private static ZooKeeper zooKeeper;


    /**
     * 建立会话
     *
     * @param args
     * @throws IOException
     * @throws InterruptedException
     * @throws KeeperException
     */
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new DeleteNode());
        System.out.println(zooKeeper.getState());

        // 计数工具类：CountDownLatch:不让main方法结束，让线程处于等待阻塞
        //countDownLatch.await();\
        Thread.sleep(Integer.MAX_VALUE);

    }


    /**
     * 回调方法：处理来自服务器端的watcher通知
     *
     * @param watchedEvent
     */
    @Override
    public void process(WatchedEvent watchedEvent) {
        // SyncConnected
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            //解除主程序在CountDownLatch上的等待阻塞
            System.out.println("process方法执行了...");
            // 删除节点
            try {
                deleteNoteSync();
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除节点的方法
     *
     * @throws KeeperException
     * @throws InterruptedException
     */
    private void deleteNoteSync() throws KeeperException, InterruptedException {

        /**
         zooKeeper.exists(path,watch) :判断节点是否存在
         zookeeper.delete(path,version) : 删除节点
         */

        Stat stat = zooKeeper.exists("/test-persistent/c1", false);
        System.out.println(stat == null ? "该节点不存在" : "该节点存在");
        if (stat != null) {
            zooKeeper.delete("/test-persistent/c1", -1);
        }
        Stat stat2 = zooKeeper.exists("/test-persistent/c1", false);
        System.out.println(stat2 == null ? "该节点不存在" : "该节点存在");
    }
}