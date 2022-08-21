package com.zk.curator.api;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * @program: zookeeper-demo
 * @author: yjl
 * @created: 2022/04/24
 * <p>
 * curator创建节点
 */
public class CreateNode {
    public static void main(String[] args) throws Exception {
        ExponentialBackoffRetry exponentialBackoffRetry = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("124.222.245.253:2181")
                .connectionTimeoutMs(50000)
                .sessionTimeoutMs(30000)
                .retryPolicy(exponentialBackoffRetry)
                .namespace("base")
                .build();

        client.start();

        String path = "/test-curator/c1";
        //创建内容为空的节点
        String s1 = client.create().forPath(path);
        //创建一个内容不为空的节点
        String s2 = client.create().forPath(path, " 我是内容".getBytes());
        //递归创建⽗节点,并选择节点类型
        String s = client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, "init".getBytes());
        System.out.println("节点递归创建成功，该节点路径" + s);

    }
}
