package com.zk.api;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @program: zookeeper-demo
 * @author: yjl
 * @created: 2022/04/24
 * 创建节点
 */
public class CreateNode implements Watcher {
    /**
     * countDownLatch这个类使⼀个线程等待,主要不让main⽅法结束
     */
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    private static ZooKeeper zooKeeper;


    /**
     * 回调方法：处理来自服务器端的watcher通知
     *
     * @param watchedEvent
     */
    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            countDownLatch.countDown();
        }
        try {
            createNodeSync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper("124.222.245.253:2181", 5000, new CreateSession());
        System.out.println(zooKeeper.getState());
        countDownLatch.await();
        //Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 创建节点
     * path: 节点创建路径
     * data[]：节点创建要保存的数据，是个byte类型的
     * acl: 节点创建的权限
     * ANYONE_ID_UNSAFE    : 表示任何人
     * *                 AUTH_IDS    ：此ID仅可用于设置ACL。它将被客户机验证的ID替换。
     * *                 OPEN_ACL_UNSAFE    ：这是一个完全开放的ACL(常用)--> world:anyone
     * *                 CREATOR_ALL_ACL  ：此ACL授予创建者身份验证ID的所有权限
     * createmode：创建节点的类型（持久节点（PERSISTENT）、持久顺序节点（PERSISTENT_SEQUENTIAL）、临时节点（EPHEMERAL）、临时顺序节点（EPHEMERAL_SEQUENTIAL））
     *
     * @throws Exception
     */
    private void createNodeSync() throws Exception {
        String node_PERSISTENT = zooKeeper.create("/test__persistent", "持久化内容".getBytes("utf-8"), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        String node_PERSISTENT_SEQUENTIAL = zooKeeper.create("/test_persistent_sequential", "持久节点内容".getBytes("utf-8"), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        String node_EPERSISTENT = zooKeeper.create("/test_ephemeral", "临时节点内容".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println(" 创建的持久节点是: " + node_PERSISTENT);
        System.out.println(" 创建的持久顺序节点是: " + node_PERSISTENT_SEQUENTIAL);
        System.out.println(" 创建的临时节点是: " + node_EPERSISTENT);
    }
}
