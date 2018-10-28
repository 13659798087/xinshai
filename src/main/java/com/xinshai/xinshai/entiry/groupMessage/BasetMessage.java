package com.xinshai.xinshai.entiry.groupMessage;

import java.util.List;

public class BasetMessage {

    private List<String> touser;//openid的集合     //private String[] touser;或者可以这样表示

    private String msgtype;//消息的类型

    public List<String> getTouser() {
        return touser;
    }

    public void setTouser(List<String> touser) {
        this.touser = touser;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }


}
