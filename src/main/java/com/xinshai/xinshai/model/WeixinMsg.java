package com.xinshai.xinshai.model;

public class WeixinMsg {

    private String id;
    private String openId;
    private String p_name;
    private String s_birthday;
    private String p_tel;
    private String createTime;

    public String getP_tel() {
        return p_tel;
    }

    public void setP_tel(String p_tel) {
        this.p_tel = p_tel;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getS_birthday() {
        return s_birthday;
    }

    public void setS_birthday(String s_birthday) {
        this.s_birthday = s_birthday;
    }


}
