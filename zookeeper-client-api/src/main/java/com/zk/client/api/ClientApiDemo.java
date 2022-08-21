package com.zk.client.api;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * @program: zookeeper-demo
 * @author: yjl
 * @created: 2022/04/24
 */
public class ClientApiDemo {

    public static void main(String[] args) throws InterruptedException {
        ZkClient zkClient = new ZkClient("124.222.245.253:2181");
        System.out.println("会话被创建了..");

        // 判断节点是否存在
        String path = "/test-zkClient-Ep";
        boolean exists = zkClient.exists(path);

        if (!exists) {
            // 创建临时节点
            zkClient.createEphemeral(path, "123");
        }

        // 读取节点内容
        Object o = zkClient.readData(path);
        System.out.println(o);

        /**
         * 注册监听
         */
        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            /**
             *当节点数据内容发生变化时，执行的回调方法
             * @param s 路径
             * @param o 变化后的节点内容
             * @throws Exception
             */
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println(s + "该节点内容被更新，更新的内容" + o);
            }

            /**
             *当节点被删除时，会执行的回调方法
             * @param s
             * @throws Exception
             */
            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println(s + "该节点被删除");
            }
        });


        // 更新节点内容
        zkClient.writeData(path, "456");
        Thread.sleep(1000);

        // 删除节点
        zkClient.delete(path);
        Thread.sleep(1000);


    }
}
