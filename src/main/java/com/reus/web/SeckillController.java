package com.reus.web;

import com.reus.dto.Exposer;
import com.reus.dto.SeckillExecution;
import com.reus.dto.SeckillResult;
import com.reus.entity.Seckill;
import com.reus.enums.SeckillEnum;
import com.reus.exception.RepeatKillException;
import com.reus.exception.SeckillCloseException;
import com.reus.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/6/4.
 */
@Controller
@RequestMapping("/seckill")//模块/资源
public class SeckillController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    //获取列表页
    public String list(Model model){
        //list.jsp
        List<Seckill> list = seckillService.getSeckillList();
        model.addAttribute("list",list);
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model){
        if(seckillId == null)
            return "redirect:/seckill/list";
        Seckill seckill = seckillService.getById(seckillId);
        if(seckill == null)
            return "forward:/seckill/list";
        model.addAttribute("seckill",seckill);
        return "detail";
    }

    //ajax json
    @RequestMapping(value = "/{seckillId}/exposer",method = RequestMethod.POST,
                    produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable Long seckillId){
        SeckillResult<Exposer> result;
        try{
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true,exposer);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            result = new SeckillResult<Exposer>(false,e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution"
                    ,method = RequestMethod.POST
                    ,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @PathVariable("md5")String md5,
                                                   @CookieValue(value = "killPhone",required = false) Long userPhone){
        SeckillResult<SeckillExecution> result;
        if(userPhone == null)
            return new SeckillResult<SeckillExecution>(false,"未注册");
        try {
//            SeckillExecution execution = seckillService.executeSeckill(seckillId,userPhone,md5);
            //存储过程调用
            SeckillExecution execution = seckillService.executeSeckillProcedure(seckillId,userPhone,md5);
            result = new SeckillResult<SeckillExecution>(true,execution);
        }catch (RepeatKillException e1){
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillEnum.REPEAT_KILL);
            result = new SeckillResult<SeckillExecution>(true,execution);
        }catch (SeckillCloseException e2){
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillEnum.END);
            result = new SeckillResult<SeckillExecution>(true,execution);
        }
        catch (Exception e){
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillEnum.INNER_ERROR);
            result = new SeckillResult<SeckillExecution>(true,execution);
        }
        return result;
    }

    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time(){
        return new SeckillResult<Long>(true,new Date().getTime());
    }
}
