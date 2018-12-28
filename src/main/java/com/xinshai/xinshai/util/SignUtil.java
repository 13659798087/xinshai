package com.xinshai.xinshai.util;

import com.xinshai.xinshai.servlet.TokenThread;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class SignUtil {

    public static Map getSign(String url) {

        Map map = new HashMap();

        //1、获取AccessToken
        String accessToken = TokenThread.accessToken.getToken();

        //2、获取Ticket
        String jsapi_ticket = WeixinUtil.getTsapiTicket( accessToken ).getTicket();

        //3、时间戳和随机字符串
        String noncestr = create_nonce_str();
        String timestamp = create_timestamp();
        //4、获取url
        //String url="http://wx.lznsn.com/personalData/personal";
        /*根据JSSDK上面的规则进行计算，这里比较简单，我就手动写啦
        String[] ArrTmp = {"jsapi_ticket","timestamp","nonce","url"};
        Arrays.sort(ArrTmp);
        StringBuffer sf = new StringBuffer();
        for(int i=0;i<ArrTmp.length;i++){
            sf.append(ArrTmp[i]);
        }
        */
        //5、将参数排序并拼接字符串
        String str = "jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;

        //6、将字符串进行sha1加密
        String signature = CheckUtil.getSha1(str);
        //System.out.println("参数："+str+"\n签名："+signature);

        map.put("jsapi_ticket",jsapi_ticket);
        map.put("noncestr",noncestr);
        map.put("timestamp",timestamp);
        map.put("signature",signature);

        return map;
    }



    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

}
