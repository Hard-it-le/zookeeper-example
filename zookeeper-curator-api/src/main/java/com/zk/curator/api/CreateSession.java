package com.zk.curator.api;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @program: zookeeper-demo
 * @author: yjl
 * @created: 2022/04/24
 * curator创建session会话
 */
public class CreateSession {

    public static void main(String[] args) {
        //不使用fluent风格

        /**
         * 重试策略接口：
         *  ExponentialBackoffRetry：基于backoff的重连策略
         *  RetryNTimes ：重连N次策略
         *  RetryForever： 永远重试策略
         */
        ExponentialBackoffRetry exponentialBackoffRetry = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("124.222.245.253:2181", exponentialBackoffRetry);
        curatorFramework.start();
        System.out.println("不使用fluent风格的会话被创建了");


        /**
         * 使用fluent风格
         *connectString： zk地址，多个地址使用英⽂逗号分隔开
         * connectionTimeoutMs：连接超时时间，默认是13S
         * sessionTimeoutMs： 会话超时时间，默认是60S
         * retryPolicy: 失败重试策略
         *      ExponentialBackoffRetry 构造器三个参数
         *      ExponentialBackoffRetry(int baseSleepTimeMs, int maxRetries, int maxSleepMs)
         *      baseSleepTimeMs： 初始的sleep时间，用于计算之后的每次重试时间
         *      maxRetries： 最大重试次数
         *      maxSleepMs： 最大sleep时间， 默认的最⼤时间是Integer.MAX_VALUE毫秒。
         *  namespace： 独立的命令空间，所有操作都以该为基础
         *  start：完成会话的创建
         */
        CuratorFramework build = CuratorFrameworkFactory.builder()
                .connectString("124.222.245.253:2181")
                .sessionTimeoutMs(50000)
                .connectionTimeoutMs(50000)
                .retryPolicy(exponentialBackoffRetry)
                .namespace("base")
                .build();

        build.start();
        System.out.println("使用fluent风格的会话被创建");
    }
}
