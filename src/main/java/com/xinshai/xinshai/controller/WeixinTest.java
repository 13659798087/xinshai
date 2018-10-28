package com.xinshai.xinshai.controller;

import com.sinosoft.demo.services.AccessTokenServices;
import com.sinosoft.demo.services.WeixinUserInfoServices;
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

        //JSONArray array=JSONArray.fromObject(a);
        JSONArray array = null;
        String openid = null;//用户的标识，对当前公众号唯一
        String nickname = null;//用户的昵称
        String sex = null;//用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
        String language = null;//用户的语言，简体中文为zh_CN
        String city = null;//用户所在城市
        String province = null;//用户所在省份
        String country = null;//用户所在国家
        String subscribe_time = null;//用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
        String groupid = null;//用户所在的分组ID（暂时兼容用户分组旧接口）

        for(int i=0;i<array.size();i++) {

            JSONObject job = array.getJSONObject(i);
            switch (job.getInt("sex")) {
                case 1:
                    sex = "男";
                    break;
                case 2:
                    sex = "女";
                case 0:
                    sex = "未知";
                    break;
                default:
                    break;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long time = Long.parseLong(job.getString("subscribe_time"));

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long lt = new Long(time);
            Date date = new Date(lt * 1000L);

            Timestamp T = Timestamp.valueOf( simpleDateFormat.format(date)  );
            System.out.println(T);

        }


        System.out.println(array);

/*
            AccessToken token = WeixinUtil.getAccessToken();

            System.out.println("------------票据----------------"+token.getToken());
            System.out.println("------------有效时间------------"+token.getExpires_in());

            String path = "E:/image/house.png";
            String media = WeixinUtil.upload(path,token.getToken(),"image");*/

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
