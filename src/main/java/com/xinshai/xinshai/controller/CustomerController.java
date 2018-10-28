package com.xinshai.xinshai.controller;

import com.alibaba.fastjson.JSON;
import com.xinshai.xinshai.entiry.customer.Message;
import com.xinshai.xinshai.entiry.customer.Staff;
import com.xinshai.xinshai.services.CustomerServices;
import com.xinshai.xinshai.servlet.TokenThread;
import com.xinshai.xinshai.util.WeixinUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/open/customer")
public class CustomerController {

    @Resource
    private CustomerServices customerServices;


    //添加客服人员
    @RequestMapping("/createStaff")
    public void createStaff(){
        Staff st = new Staff();
        st.setKf_account("guo@Staying-myshell");
        st.setNickname("小国");
        st.setPassword("123456");
        String sa = JSON.toJSONString(st);
        int result = WeixinUtil.addCustomer(TokenThread.accessToken.getToken(),sa);
    }


    //添加客服人员
    @RequestMapping("/customSend")
    public void customSendcustomSend(){
        Message me = new Message();
        Map<String,Object> text = new HashMap<String,Object>();
        text.put("content","Hello World");

        me.setTouser("ofhhXxFRKiAg5NJgPCSe1qlbirS8");
        me.setMsgtype("text");
        me.setText(text);

        String sa = JSON.toJSONString(me);
        int result = WeixinUtil.addCustomer(TokenThread.accessToken.getToken(),sa);
    }



}
