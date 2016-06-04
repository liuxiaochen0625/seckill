package com.reus.dto;

/**
 * Created by Administrator on 2016/6/4.
 */
public class Exposer {
    //加密方式
    private String md5;
    private long start;
    private long end;
    private long now;
    //是否开启秒杀
    private boolean exposed;
    private long seckillId;

    public Exposer(String md5, long seckillId, boolean exposed) {
        this.md5 = md5;
        this.seckillId = seckillId;
        this.exposed = exposed;
    }

    public Exposer(long seckillId,long start, long end, long now, boolean exposed) {
        this.start = start;
        this.end = end;
        this.now = now;
        this.exposed = exposed;
        this.seckillId = seckillId;
    }

    public Exposer(boolean exposed, long seckillId) {
        this.exposed = exposed;
        this.seckillId = seckillId;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public boolean isExposed() {
        return exposed;
    }

    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    @Override
    public String toString() {
        return "Exposer{" +
                "md5='" + md5 + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", now=" + now +
                ", exposed=" + exposed +
                ", seckillId=" + seckillId +
                '}';
    }
}
