-- 秒杀执行存储过程
-- console ;转换为 $$ 即分隔符由原来的;转化为$$
DELIMITER $$
-- 定义存储过程
-- in 输入参数，out 输出参数;row_count()返回上一条修改类型sql（insert,update,delete）的影响行数
-- row_count:0 未修改，>0修改的行数，<0 sql错误/未执行修改sql
CREATE PROCEDURE `scukill`.`execute_seckill`
  (in v_seckill_id bigint,in v_phone bigint,in v_kill_time timestamp,
   out r_result int)
  BEGIN
    DECLARE insert_count int DEFAULT 0;
    START TRANSACTION;
    insert ignore into success_killed
    (seckill_id,user_phone,create_time)
    VALUES (v_seckill_id,v_phone,v_kill_time);
    select row_count() into insert_count;
    IF (insert_count = 0) THEN
      ROLLBACK;
      set r_result = -1;
    ELSEIF (insert_count < 0 ) THEN
      ROLLBACK;
      SET r_result = -2;
    ELSE
      UPDATE seckill
      SET number = number - 1
      WHERE seckill_id = v_seckill_id
            AND end_time > v_kill_time
            AND start_time < v_kill_time
            AND number > 0;
      SELECT ROW_COUNT() INTO insert_count;
      IF (inset_count = 0) THEN
        ROLLBACK;
        SET r_result = 0;
      elseif(insert_count < 0) THEN
        ROLLBACK;
        SET r_result = -2;
      ELSE
        COMMIT ;
        SET r_result = 1;
      END IF;
    END IF;
  END;
$$

-- 存储过程定义结束


DELIMITER ;
set @r_result = -3;
CALL execute_seckill(1003,15868180410,now(),@r_result);

-- 获取结果
SELECT @r_result;