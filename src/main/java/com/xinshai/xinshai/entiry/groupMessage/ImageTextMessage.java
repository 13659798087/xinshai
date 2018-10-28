package com.xinshai.xinshai.entiry.groupMessage;


import java.util.Map;


/**
 * 图文消息
 */
public class ImageTextMessage extends BasetMessage {

    private Map<String,Object> mpnews;//数据格式是键值对

    private long send_ignore_reprint;

    public Map<String, Object> getMpnews() {
        return mpnews;
    }

    public void setMpnews(Map<String, Object> mpnews) {
        this.mpnews = mpnews;
    }

    public long getSend_ignore_reprint() {
        return send_ignore_reprint;
    }

    public void setSend_ignore_reprint(Integer send_ignore_reprint) {
        this.send_ignore_reprint = send_ignore_reprint;
    }



}
