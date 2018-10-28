package com.xinshai.xinshai.services;


import com.xinshai.xinshai.dao.AccessTokenDao;
import com.xinshai.xinshai.entiry.AccessToken;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AccessTokenServices {

    @Resource
    private AccessTokenDao accessTokenDao;

    public AccessToken getAccessToken(){
        return accessTokenDao.getAccessToken();
    }

    public void updateToken(String token, long lastTime) {
        accessTokenDao.updateToken( token, lastTime);
    }

}
