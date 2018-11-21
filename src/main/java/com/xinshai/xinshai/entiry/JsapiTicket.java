package com.xinshai.xinshai.entiry;

public class JsapiTicket {


    private String ticket;

    private Integer expires_in;//凭证有效时间

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }
}
