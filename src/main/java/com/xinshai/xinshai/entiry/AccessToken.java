package com.xinshai.xinshai.entiry;

public class AccessToken {

    private String token;//凭证

    private Integer expires_in;//凭证有效时间

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }


}
