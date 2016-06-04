package com.reus.dao;

import com.reus.entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/6/3.
 */
public interface SeckillDao {
    /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime")Date killTime);

    /**
     * 根据id查询商品
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     *  根据offset和limit查询产品列表
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset")int offset,@Param("limit")int limit);
}
