package com.example.sonliu.drinkingalert.datebase.data;

import java.io.Serializable;

public class Alarm implements Serializable {
    private Integer id;
    private Integer uid;//用户id
    private Integer time;//闹钟时间 分钟数表示

    public Alarm() {

    }

    public Alarm(Integer id, Integer uid, Integer time) {
        this.id = id;
        this.uid = uid;
        this.time = time;
    }

    public Alarm(Integer uid, Integer time) {
        this.uid = uid;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "id=" + id +
                ", uid=" + uid +
                ", time=" + time +
                '}';
    }
}
