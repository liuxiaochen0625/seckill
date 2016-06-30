package com.reus.service;

import com.reus.dto.Exposer;
import com.reus.dto.SeckillExecution;
import com.reus.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/6/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"
})
public class SeckillServiceTest {
    private Logger logger = LoggerFactory.getLogger(SeckillServiceTest.class);
    @Autowired
    private SeckillService seckillService;
    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> seckills = seckillService.getSeckillList();
        logger.info("list={}",seckills);
    }

    @Test
    public void getById() throws Exception {
        Seckill seckill = seckillService.getById(1000L);
        logger.info("seckill={}",seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        Exposer exposer = seckillService.exportSeckillUrl(1000L);
        logger.info(exposer.toString());

    }

    @Test
    public void executeSeckill() throws Exception {
        SeckillExecution execution = seckillService.executeSeckill(1000,15868180411L,"7bc9ebebabb05733cb0ec17a0fd6d426");
        logger.info(execution.toString());
    }

    @Test
    public void executeSeckillProcedureTest(){
        long seckillId = 1001;
        long phone = 11111111111L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if(exposer.isExposed()){
            String md5 = exposer.getMd5();
           SeckillExecution execution = seckillService.executeSeckillProcedure(seckillId,phone,md5);
            System.out.println(execution.getStateInfo());
        }
    }

}