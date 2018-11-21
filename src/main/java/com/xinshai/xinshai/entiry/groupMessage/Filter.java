package com.xinshai.xinshai.entiry.groupMessage;

public class Filter {

    private Boolean is_to_all;
    private String tag_id;

    public Boolean getIs_to_all() {
        return is_to_all;
    }

    public void setIs_to_all(Boolean is_to_all) {
        this.is_to_all = is_to_all;
    }

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
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