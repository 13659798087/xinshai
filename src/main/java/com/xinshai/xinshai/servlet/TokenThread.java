package com.xinshai.xinshai.servlet;

import com.xinshai.xinshai.entiry.AccessToken;
import com.xinshai.xinshai.entiry.JsapiTicket;
import com.xinshai.xinshai.util.WeixinUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定时获取微信access_token的线程
 *
 * @author liuyq
 * @date 2013-05-02
 */
public class TokenThread implements Runnable {

    private static Logger log = LoggerFactory.getLogger(TokenThread.class);

    public static AccessToken accessToken = null;

    public static JsapiTicket jsapiTicket = null;


    //将Token存到到全局变量，通过TokenThread.accessToken.getToken()取得Token;
    public void run() {

        //创建菜单,暂时注释
       /* String menu  = JSONObject.fromObject(WeixinUtil.initMenu()).toString();
        int result = 0;

        result = WeixinUtil.createMenu(WeixinUtil.getAccessToken().getToken(),menu);//创建菜单

        if(result==0){
            System.out.println("创建菜单成功");
        }else{
            System.out.println("错误码"+result);
        }*/

        while (true) {
            try {
                accessToken = WeixinUtil.getAccessToken();

                if (null != accessToken) {
                    //jsapiTicket = WeixinUtil.getTsapiTicket( accessToken.getToken() );
                    log.info("获取access_token成功，有效时长{}秒 token:{}", accessToken.getExpires_in(), accessToken.getToken());
                    // 休眠7000秒
                    Thread.sleep((accessToken.getExpires_in() - 200) * 1000);
                } else {
                    // 如果access_token为null，60秒后再获取
                    Thread.sleep(60 * 1000);
                }
            } catch (InterruptedException e) {
                try {
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException e1) {
                    log.error("{}", e1);
                }
                log.error("{}", e);
            }



            /*try {
                jsapiTicket = WeixinUtil.getTsapiTicket( accessToken.getToken() );
                if (null != jsapiTicket) {
                    log.info("获取jsapi_ticket成功，有效时长{}秒 token:{}", jsapiTicket.getExpires_in(), jsapiTicket.getTicket());
                    // 休眠7000秒
                    Thread.sleep((jsapiTicket.getExpires_in() - 200) * 1000);
                } else {
                    // 如果access_token为null，60秒后再获取
                    Thread.sleep(60 * 1000);
                }
            } catch (InterruptedException e) {
                try {
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException e1) {
                    log.error("{}", e1);
                }
                log.error("{}", e);
            }*/


        }
    }
}