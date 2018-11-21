package com.xinshai.xinshai.entiry;

public class LocationParams {

    private String location;// 否 Object(location) 地点
    private String name;//  餐馆名称
    private String category;// 否 String 餐馆类型/菜系
    private String special;// 否 String 菜名
    private String price;// 否 Object(number) 价格（单位元）
    private String radius;// 否 Object(number) 距离（单位米）
    private Integer coupon;// 否 Int 优惠信息：0 无（默认），1 优惠券，2 团购
    private Integer sort;//排序类型：0 距离（默认），1 点评高优先级，2 服务质量高优先级，3 环境高优先级，4 价格高到低，5价格低到高

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public Integer getCoupon() {
        return coupon;
    }

    public void setCoupon(Integer coupon) {
        this.coupon = coupon;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
