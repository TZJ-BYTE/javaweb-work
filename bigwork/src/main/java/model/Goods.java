package model;

public class Goods {
    private Integer id;
    private String goodsname;
    private float price;
    private String taste;
    private String dietHabit;
    private String healthRequirement;

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
