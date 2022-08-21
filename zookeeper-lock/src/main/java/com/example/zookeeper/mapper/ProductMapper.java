package com.example.zookeeper.mapper;

import com.example.zookeeper.entity.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author yujiale
 */
public interface ProductMapper {
    /**
     * 获取消费者
     * @param id
     * @return
     */
    @Select(" select * from product where id=#{id}  ")
    Product getProduct(@Param("id") Integer id);

    /**
     * 扣件库存
     * @param id
     * @return
     */
    @Update(" update product set stock=stock-1    where id=#{id}  ")
    int deductStock(@Param("id") Integer id);
}
