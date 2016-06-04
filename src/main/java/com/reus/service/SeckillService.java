package com.reus.service;

import com.reus.dto.Exposer;
import com.reus.dto.SeckillExecution;
import com.reus.entity.Seckill;
import com.reus.exception.RepeatKillException;
import com.reus.exception.SeckillCloseException;
import com.reus.exception.SeckillException;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/6/4.
 */
public interface SeckillService {
    /**
     * 查询所有秒杀商品
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀产品
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 输出秒杀接口地址
     * @param seckillId
     * @return exposer
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException,RepeatKillException,SeckillCloseException;


}
