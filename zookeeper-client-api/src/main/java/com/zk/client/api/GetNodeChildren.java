package com.zk.client.api;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

/**
 * @program: zookeeper-demo
 * @author: yjl
 * @created: 2022/04/24
 */
public class GetNodeChildren {


    public static void main(String[] args) throws InterruptedException {
        ZkClient zkClient = new ZkClient("124.222.245.253:2181");
        System.out.println("会话被创建了..");
        // 获取子节点列表
        List<String> children = zkClient.getChildren("/test-zkclient");
        System.out.println(children);
        /**
         *  注册监听事件
         客户端可以对一个不存在的节点进行子节点变更的监听
         只要该节点的子节点列表发生变化，或者该节点本身被创建或者删除，都会触发监听
         */
        zkClient.subscribeChildChanges("/test-zkclient-get", new IZkChildListener() {
            /**
             * @param parentPath parentPath
             * @param list 变化后子节点列表
             * @throws Exception
             */
            @Override
            public void handleChildChange(String parentPath, List<String> list) throws Exception {
                System.out.println(parentPath + "的子节点列表发生了变化,变化后的子节点列表为" + list);
            }
        });

        //测试
        zkClient.createPersistent("/test-zkclient-get");
        Thread.sleep(1000);

        zkClient.createPersistent("/test-zkclient-get/c1");
        Thread.sleep(1000);


    }
}
