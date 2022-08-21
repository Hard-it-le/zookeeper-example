package com.zk.curator.api;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @program: zookeeper-demo
 * @author: yjl
 * @created: 2022/04/24
 * curator删除节点
 */
public class DeleteNode {
    public static void main(String[] args) throws Exception {
        //不使用fluent编程风格

        RetryPolicy exponentialBackoffRetry = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("127.0.0.1:2181", exponentialBackoffRetry);
        curatorFramework.start();
        System.out.println("会话被建立了");

        // 使用fluent编程风格
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("124.222.245.253:2181")
                .sessionTimeoutMs(50000)
                .connectionTimeoutMs(30000)
                .retryPolicy(exponentialBackoffRetry)
                .namespace("base")
                .build();

        client.start();

        System.out.println("会话2创建了");

        // 删除节点
        String path = "/test-curator";
        //删除⼀个⼦节点
        client.delete().forPath(path);
        //删除节点并递归删除其⼦节点
        client.delete().deletingChildrenIfNeeded().withVersion(-1).forPath(path);
        //指定版本进⾏删除
        client.delete().withVersion(1).forPath(path);
        //强制保证删除⼀个节点
        client.delete().guaranteed().forPath(path);
        System.out.println("删除成功，删除的节点" + path);

    }
}
