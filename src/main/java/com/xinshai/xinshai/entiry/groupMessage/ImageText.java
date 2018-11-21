package com.xinshai.xinshai.entiry.groupMessage;

import java.util.Map;

public class ImageText {

    private Filter filter;
    private Map<String,Object> mpnews;
    private String msgtype;
    private Integer send_ignore_reprint;

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public Map<String, Object> getMpnews() {
        return mpnews;
    }

    public void setMpnews(Map<String, Object> mpnews) {
        this.mpnews = mpnews;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public Integer getSend_ignore_reprint() {
        return send_ignore_reprint;
    }

    public void setSend_ignore_reprint(Integer send_ignore_reprint) {
        this.send_ignore_reprint = send_ignore_reprint;
    }
}
/*
{
        "filter":{
        "is_to_all":false,
        "tag_id":2
        },
        "mpnews":{
        "media_id":"123dsdajkasd231jhksad"
        },
        "msgtype":"mpnews",
        "send_ignore_reprint":0
        }*/
