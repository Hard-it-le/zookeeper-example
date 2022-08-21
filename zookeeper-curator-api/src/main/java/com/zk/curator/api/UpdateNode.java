package com.zk.curator.api;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;

/**
 * @program: zookeeper-demo
 * @author: yjl
 * @created: 2022/04/24
 */
public class UpdateNode {
    // 创建会话
    public static void main(String[] args) throws Exception {

        //不使用fluent编程风格
        RetryPolicy exponentialBackoffRetry = new ExponentialBackoffRetry(1000, 3);

        // 使用fluent编程风格
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(50000)
                .connectionTimeoutMs(30000)
                .retryPolicy(exponentialBackoffRetry)
                .namespace("base")
                .build();
        client.start();
        System.out.println("会话2创建了");

        // 创建节点
        String path = "/test-curator/c1";

        // 获取节点的数据内容及状态信息
        // 数据内容
        byte[] bytes = client.getData().forPath(path);
        System.out.println("获取到的节点数据内容：" + new String(bytes));

        // 状态信息
        Stat stat = new Stat();
        client.getData().storingStatIn(stat).forPath(path);
        System.out.println("获取到的节点状态信息：" + stat);
        // 更新节点内容 //1
        int version = client.setData().withVersion(stat.getVersion()).forPath(path, "修改内容1".getBytes()).getVersion();
        System.out.println("当前的最新版本是" + version);
        byte[] bytes2 = client.getData().forPath(path);
        System.out.println("修改后的节点数据内容：" + new String(bytes2));
        // BadVersionException
        client.setData().withVersion(stat.getVersion()).forPath(path, "修改内容2".getBytes());


    }

}
