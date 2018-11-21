package com.xinshai.xinshai.controller;


import com.xinshai.xinshai.services.AccessTokenServices;
import com.xinshai.xinshai.services.WeixinMenuServices;
import com.xinshai.xinshai.services.WeixinUserInfoServices;
import com.xinshai.xinshai.servlet.TokenThread;
import com.xinshai.xinshai.util.WeixinUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/abc")
public class time {


    @RequestMapping("/test")
    public void test() {
        String nextOpenid="";
        String openidList = null;
        openidList = WeixinUtil.getUserList( TokenThread.accessToken.getToken() ,nextOpenid);
        String [] stringArr= openidList.replace("[","").replace("]","").split(",");
    }



}








