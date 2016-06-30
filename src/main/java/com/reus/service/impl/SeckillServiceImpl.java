package com.reus.service.impl;

import com.reus.dao.SeckillDao;
import com.reus.dao.SucessKilledDao;
import com.reus.dao.cache.RedisDao;
import com.reus.dto.Exposer;
import com.reus.dto.SeckillExecution;
import com.reus.entity.Seckill;
import com.reus.entity.SuccessKilled;
import com.reus.enums.SeckillEnum;
import com.reus.exception.RepeatKillException;
import com.reus.exception.SeckillCloseException;
import com.reus.exception.SeckillException;
import com.reus.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/6/4.
 */
@Service
public class SeckillServiceImpl implements SeckillService{
    private Logger logger = LoggerFactory.getLogger(SeckillServiceImpl.class);

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SucessKilledDao sucessKilledDao;

    @Autowired
    private RedisDao redisDao;
    private final String slat = "wqwefe223290<>,../[]'';;[";
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,10);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }


    public Exposer exportSeckillUrl(long seckillId) {
        //访问redis
        Seckill seckill = redisDao.getSeckill(seckillId);
        if(seckill == null){
            //访问数据库
            seckill = seckillDao.queryById(seckillId);
            if(seckill == null){
                return new Exposer(false,seckillId);
            }else {
                //放入redis中
                redisDao.putSeckill(seckill);
            }

        }
        if(seckill == null){
            return new Exposer(false,seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        if(nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime())
            return new Exposer(seckillId,startTime.getTime(),endTime.getTime(),nowTime.getTime(),false);
        String md5 = getMD5(seckillId);
        return new Exposer(md5,seckillId,true);
    }

    @Transactional
    /**
     * 使用注解控制事务方法的优点：
     * 1.
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException {
        if(md5 == null || !md5.equals(getMD5(seckillId)))
            throw new SecurityException("seckill date rewrite");
        int updateCount = seckillDao.reduceNumber(seckillId,new Date());
        //减库存
        try {
            if(updateCount <=0 ){
                throw new SeckillCloseException("seckill is closed!");
            }else {
                //插入购买明细
                int insertCount = sucessKilledDao.insertSuccessKilled(seckillId, userPhone);
                if (insertCount <= 0)
                    throw new RepeatKillException("seckill repeat");
                else {
                    SuccessKilled successKilled = sucessKilledDao.queryByIdWithSeckill(seckillId,userPhone);
                    return new SeckillExecution(seckillId, SeckillEnum.SUCCESS,successKilled);
                }
            }
        }catch (SeckillCloseException e1){
            throw e1;
        }catch (RepeatKillException e2){
            throw e2;
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new SeckillException("seckill inner error:"+e.getMessage());
        }

    }

    /**
     * 生成md5
     * @param seckillId
     * @return
     */
    private String getMD5(long seckillId){
        String base = seckillId + "/" + slat;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
}
