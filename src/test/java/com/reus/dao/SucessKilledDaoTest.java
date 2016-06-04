package com.reus.dao;

import com.reus.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/6/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SucessKilledDaoTest {
    @Resource
    private SucessKilledDao sucessKilledDao;
    @Test
    public void insertSuccessKilled() throws Exception {
        int number = sucessKilledDao.insertSuccessKilled(1000L,15868180410L);
        System.out.println(number);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        SuccessKilled successKilled = sucessKilledDao.queryByIdWithSeckill(1000L,15868180410L);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }

}