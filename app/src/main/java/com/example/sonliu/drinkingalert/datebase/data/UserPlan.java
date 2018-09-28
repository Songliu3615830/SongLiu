package com.example.sonliu.drinkingalert.datebase.data;

import java.io.Serializable;

public class UserPlan implements Serializable {
    private Integer id;
    private Integer type;// 0 成人 1 孩子
    private Integer icon;//头像id
    private String name;
    private Integer dayDrink;//每日计划喝水量
    private Integer isAlert;// 0 否 1 是
    private Integer typeAlert;//提醒方式 0 无声 1 仅振动 2 仅提示音 3 震动加提示音

    public UserPlan() {

    }

    public UserPlan(Integer id, Integer type, Integer icon, String name, Integer dayDrink, Integer isAlert, Integer typeAlert) {
        this.id = id;
        this.type = type;
        this.icon = icon;
        this.name = name;
        this.dayDrink = dayDrink;
        this.isAlert = isAlert;
        this.typeAlert = typeAlert;
    }

    public UserPlan(Integer type, Integer icon, String name, Integer dayDrink, Integer isAlert, Integer typeAlert) {
        this.type = type;
        this.icon = icon;
        this.name = name;
        this.dayDrink = dayDrink;
        this.isAlert = isAlert;
        this.typeAlert = typeAlert;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDayDrink() {
        return dayDrink;
    }

    public void setDayDrink(Integer dayDrink) {
        this.dayDrink = dayDrink;
    }

    public Integer getIsAlert() {
        return isAlert;
    }

    public void setIsAlert(Integer alert) {
        isAlert = alert;
    }

    public Integer getTypeAlert() {
        return typeAlert;
    }

    public void setTypeAlert(Integer typeAlert) {
        this.typeAlert = typeAlert;
    }

    @Override
    public String toString() {
        return "UserPlan{" +
                "id=" + id +
                ", type=" + type +
                ", icon=" + icon +
                ", name='" + name + '\'' +
                ", dayDrink=" + dayDrink +
                ", isAlert=" + isAlert +
                ", typeAlert=" + typeAlert +
                '}';
    }
}
