package com.zk.client.api;

import org.I0Itec.zkclient.ZkClient;

/**
 * @program: zookeeper-demo
 * @author: yjl
 * @created: 2022/04/24
 */
public class DeleteNode {

    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("124.222.245.253:2181");
        System.out.println("会话被创建了..");
        // 递归删除节点
        String path = "/test-zkClient";
//        zkClient.createPersistent(path + "/c11");
        zkClient.deleteRecursive(path);
        System.out.println("递归删除成功");


    }
}
