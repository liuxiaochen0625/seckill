package com.reus.enums;

/**
 * Created by Administrator on 2016/6/4.
 */
public enum  SeckillEnum {
    SUCCESS(1,"秒杀成功"),
    END(0,"秒杀结束"),
    REPEAT_KILL(-1,"重复秒杀"),
    INNER_ERROR(-2,"系统异常"),
    DATE_REWRITE(-3,"数据串改");
    private int state;
    private String stateInfo;

    SeckillEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }


    public static SeckillEnum stateOf(int index) {
        for (SeckillEnum seckillEnum : values()) {
            if (seckillEnum.getState() == index) {
                return seckillEnum;
            }
        }
        return null;
    }
}
