package com.reus.dao;

import com.reus.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 配置spring和junit整合，junit启动时加载springIOC
 * Created by Administrator on 2016/6/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void testReduceNumber() throws Exception{
        long id = 1000;
        Date date = new Date();
        int number = seckillDao.reduceNumber(id,date);
        System.out.println(number);

    }
    @Test
    public void testQueryById(){
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill);
    }
    @Test
    public void testQueryAll(){
        List<Seckill> seckills = seckillDao.queryAll(0,100);
        System.out.println(seckills);
    }
}
