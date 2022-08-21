package com.zk.client.api;

import org.I0Itec.zkclient.ZkClient;

/**
 * @program: zookeeper-demo
 * @author: yjl
 * @created: 2022/04/24
 * zkclient完成会话的创建
 */
public class CreateSession {
    /**
     * 创建一个zkclient实例就可以完成连接，完成会话的创建
     * serverString : 服务器连接地址
     * 注意：zkClient通过对zookeeperAPI内部封装，将这个异步创建会话的过程同步化了..
     *
     * @param args
     */
    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("124.222.245.253:2181");
        System.out.println("会话被创建了");
    }
}
