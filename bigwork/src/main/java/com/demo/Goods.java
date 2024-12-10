package com.demo;

import java.util.Date;

public class Goods {
    private Integer id;
    private String goodsname;
    private float price;
    private String pic;
    private String factory;
    private Date outdate;
    private String introduction;
    private String taste;
    private String dietHabit;
    private String healthRequirement;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public Date getOutdate() {
        return outdate;
    }

    public void setOutdate(Date outdate) {
        this.outdate = outdate;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public String getDietHabit() {
        return dietHabit;
    }

    public void setDietHabit(String dietHabit) {
        this.dietHabit = dietHabit;
    }

    public String getHealthRequirement() {
        return healthRequirement;
    }

    public void setHealthRequirement(String healthRequirement) {
        this.healthRequirement = healthRequirement;
    }
}
