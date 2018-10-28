package com.xinshai.xinshai.entiry;

public class WeiXinUserList {

    // 总关注用户数
    private Integer  total;
    // 获取的OpenId个数
    private Integer  count;
    // OpenId列表
    private WeiXinUserData data;
    // 最后一个用户的openid
    private String next_openid;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public WeiXinUserData getData() {
        return data;
    }

    public void setData(WeiXinUserData data) {
        this.data = data;
    }

    public String getNext_openid() {
        return next_openid;
    }

    public void setNext_openid(String next_openid) {
        this.next_openid = next_openid;
    }
}
