package com.zk.api;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @program: zookeeper-demo
 * @author: yjl
 * @created: 2022/04/24
 * <p>
 * 获取节点数据
 */
public class GetNodeData implements Watcher {
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
        zooKeeper = new ZooKeeper("124.222.245.253:2181", 5000, new GetNodeData());
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

        /**
         *  子节点列表发生改变时，服务器端会发生noteChildrenChanged事件通知
         *             要重新获取子节点列表，同时注意：通知是一次性的，需要反复注册监听
         */
        if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {

            List<String> children = null;
            try {
                children = zooKeeper.getChildren("/test-persistent", true);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(children);

        }

        // SyncConnected
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            //解除主程序在CountDownLatch上的等待阻塞
            System.out.println("process方法执行了...");
            // 获取节点数据的方法
            try {
                getNoteData();
                // 获取节点的子节点列表方法
                getChildrens();
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * 获取某个节点的内容
     *
     *  @throws KeeperException
     * @throws InterruptedException
     */
    private void getNoteData() throws KeeperException, InterruptedException {

        /** path:获取数据的路径
         * watch:是否开启监听
         * stat:节点状态信息
         * null:表示获取最新版本的数据
         * zk.getData(path, watch, stat);
         */
        byte[] data = zooKeeper.getData("/test-persistent", false, null);
        System.out.println(new String(data));
    }


    /**
     * 获取某个节点的子节点列表方法
     *
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static void getChildrens() throws KeeperException, InterruptedException {

        /** getChildren参数说明
         *  path:路径
         *   watch:是否要启动监听，当子节点列表发生变化，会触发监听
         *   zooKeeper.getChildren(path, watch);
         */
        List<String> children = zooKeeper.getChildren("/test-persistent", true);
        System.out.println(children);
    }
}
