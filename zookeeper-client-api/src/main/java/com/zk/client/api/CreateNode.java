package com.zk.client.api;

import org.I0Itec.zkclient.ZkClient;

/**
 * @program: zookeeper-demo
 * @author: yjl
 * @created: 2022/04/24
 */
public class CreateNode {

    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("124.222.245.253:2181");
        System.out.println("会话被创建了..");

        /**
         * 创建节点
         cereateParents : 是否要创建父节点，如果值为true,那么就会递归创建节点
         */
        zkClient.createPersistent("/test-zkclient/c1", true);
        zkClient.createPersistent("/test-zkClient/c2", true);
        System.out.println("节点递归创建完成");


    }

}
