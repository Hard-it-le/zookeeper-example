package com.example.zookeeper;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yujiale
 */
@SpringBootApplication
@MapperScan("com.example.zookeeper.mapper")
public class ZookeeperLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZookeeperLockApplication.class, args);
    }

}
