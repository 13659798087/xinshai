package com.xinshai.xinshai.controller;

import com.xinshai.xinshai.entiry.AccessToken;
import com.xinshai.xinshai.services.AccessTokenServices;
import com.xinshai.xinshai.services.WeixinUserInfoServices;
import com.xinshai.xinshai.util.WeixinUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class WeixinTest {

    @Resource
    private AccessTokenServices accessTokenServices;

    @Resource
    private WeixinUserInfoServices weixinUserInfoServices;


    public static void main(String[] args) throws IOException {

            /*AccessToken token = WeixinUtil.getAccessToken();
            System.out.println("------------票据----------------"+token.getToken());
            System.out.println("------------有效时间------------"+token.getExpires_in());
            String path = "E:/image/asd.jpg";*/



            //String media = WeixinUtil.upload(path,token.getToken(),"image");

            //String media = WeixinUtil.upload(path,token.getToken(),"thumb");
           //System.out.println(media);

           /*} String menu  = JSONObject.fromObject(WeixinUtil.initMenu()).toString();
            int result = WeixinUtil.createMenu(token.getToken(),menu);
            if(result==0){
                System.out.println("创建菜单成功");
            }else{
                System.out.println("错误码"+result);
            */

           /* JSONObject jsonObject = WeixinUtil.queryMenu(token.getToken());
            System.out.println(jsonObject);

            int result = WeixinUtil.deleteMenu(token.getToken());
            if(result==0){
                System.out.println("菜单删除成功");
            }else {
                System.out.println(result);
            }*/



            //System.out.println(result);


    }


}
