package com.xinshai.xinshai.entiry.groupMessage;

import java.util.Map;

public class PreviewMessage {

    private String touser;//消息的类型
    private String msgtype;//消息的类型
    private Map<String,Object> mpnews;//数据格式是键值对

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public Map<String, Object> getMpnews() {
        return mpnews;
    }

    public void setMpnews(Map<String, Object> mpnews) {
        this.mpnews = mpnews;
    }
}
