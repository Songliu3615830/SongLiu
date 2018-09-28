package com.example.sonliu.drinkingalert.datebase.data;

import java.io.Serializable;

public class Cup implements Serializable {
    private Integer id;
    private Integer uid;
    private Integer img;
    private Integer num;

    public Cup() {

    }

    public Cup(Integer uid, Integer img, Integer num) {
        this.uid = uid;
        this.img = img;
        this.num = num;
    }

    public Cup(Integer id, Integer uid, Integer img, Integer num) {
        this.id = id;
        this.uid = uid;
        this.img = img;
        this.num = num;
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

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Cup{" +
                "id=" + id +
                ", uid=" + uid +
                ", img=" + img +
                ", num=" + num +
                '}';
    }
}
