package com.example.sonliu.drinkingalert.datebase.data;

import java.io.Serializable;

public class DrinkRecord implements Serializable {
    private Integer id;
    private Integer uid;
    private Integer num;
    private Long time;

    public DrinkRecord() {

    }

    public DrinkRecord(Integer id, Integer uid, Integer num, Long time) {
        this.id = id;
        this.uid = uid;
        this.num = num;
        this.time = time;
    }

    public DrinkRecord(Integer uid, Integer num, Long time) {
        this.uid = uid;
        this.num = num;
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "DrinkRecord{" +
                "id=" + id +
                ", uid=" + uid +
                ", num=" + num +
                ", time=" + time +
                '}';
    }
}
