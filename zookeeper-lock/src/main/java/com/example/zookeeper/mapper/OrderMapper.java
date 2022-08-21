package com.example.zookeeper.mapper;

import com.example.zookeeper.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

/**
 * @author yujiale
 */
public interface OrderMapper {
    /**
     *
     * 插入
     * @param order
     * @return
     */
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    @Insert(" insert into `order`(user_id,pid) values(#{userId},#{pid}) ")
    int insert(Order order);
}
