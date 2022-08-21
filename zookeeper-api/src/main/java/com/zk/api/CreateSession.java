package com.zk.api;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @program: zookeeper-demo
 * @author: yjl
 * @created: 2022/04/24
 * <p>
 * 创建会话
 */
public class CreateSession implements Watcher {
    /**
     * countDownLatch这个类使⼀个线程等待,主要不让main⽅法结束
     */
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    /**
     * 当前类实现了Watcher接⼝，重写了process⽅法，
     * 该⽅法负责处理来⾃Zookeeper服务端的watcher通知，
     * 在收到服务端发送过来的SyncConnected事件之后，解除主程序在CountDownLatch上的等待阻塞，
     * ⾄此，会话创建完毕
     *
     * @param watchedEvent
     */
    @Override
    public void process(WatchedEvent watchedEvent) {
        //当连接创建了，服务端发送给客户端 SyncConnected事件
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            countDownLatch.countDown();
        }
    }

    /**
     * ZooKeeper客户端和服务端会话的建⽴是⼀个异步的过程，
     * 也就是说在程序中，构造⽅法会在处理完客户端初始化⼯作后⽴即返回，
     * 在⼤多数情况下，此时并没有真正建⽴好⼀个可⽤的会话，
     * 在会话的⽣命周期中处于“CONNECTING”的状态。
     * 当该会话真正创建完毕后ZooKeeper服务端会向会话对应的客户端发送⼀个事件通知，以告知客户端，
     * 客户端只有在获取这个通知之后，才算真正建⽴了会话。
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        /**客户端可以通过创建⼀个zk实例来连接zk服务器
         new Zookeeper(connectString,sesssionTimeOut,Wather)
         connectString:连接地址：IP：端⼝
         sesssionTimeOut：会话超时时间：单位毫秒
         Wather：监听器(当特定事件触发监听时，zk会通过watcher通知到客户端)
         */
        ZooKeeper zooKeeper = new ZooKeeper("124.222.245.253:2181", 5000, new CreateSession());
        System.out.println(zooKeeper.getState());
        System.out.println("==========Client  connected to zookeeper =====");
    }
}
