package com.xinshai.xinshai.controller;

import com.xinshai.xinshai.servlet.TokenThread;
import com.xinshai.xinshai.util.WeixinUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@Controller
@RequestMapping("/ABC")
public class SendGroupMessage {


    /**
     * 群发文本消息
     * @return
     */
    @ResponseBody
    @RequestMapping("/ABC")
    public int ABC() {
        String textMessage = JSONObject.fromObject(WeixinUtil.initTextGroupMessage()).toString();

        int result = WeixinUtil.SendTextMessage(TokenThread.accessToken.getToken(),textMessage);

        if(result==0){
            System.out.println("发送文本消息成功");
        }else {
            System.out.println("错误码" + result);
        }
        return result;
    }

    /**
     * 根据openid群发图文消息
     * @return
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/DEF")
    public int DEF() throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException {

        String path = "D:/dgfd.jpg";
        String media_id = WeixinUtil.upload(path, TokenThread.accessToken.getToken(),"image");

        String groupMessage = JSONObject.fromObject(WeixinUtil.initImageTextGroupMessage(media_id)).toString();

        int result = WeixinUtil.SendTextMessage(TokenThread.accessToken.getToken(),groupMessage);

        if(result==0){
            System.out.println("发送成功");
        }else {
            System.out.println("错误码" + result);
        }

        return result;

    }


    /**
     * 根据标签群发图文消息
     * @return
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/adf")
    public int adf() throws IOException {

        String path = "D:/book.jpg";
        String media_id = WeixinUtil.upload(path, TokenThread.accessToken.getToken(),"image");

        String groupMessage = JSONObject.fromObject(WeixinUtil.initTagMessage(media_id)).toString();

        int result = WeixinUtil.SendTagMessage(TokenThread.accessToken.getToken(),groupMessage);

        if(result==0){
            System.out.println("发送成功");
        }else {
            System.out.println("错误码" + result);
        }

        return result;

    }


    @ResponseBody
    @RequestMapping("/previewMessage")
    public int previewMessage() throws IOException {

        String path = "E:/image/qwe.JPG";
        String media_id = WeixinUtil.upload(path, TokenThread.accessToken.getToken(),"image");

        String groupMessage = JSONObject.fromObject(WeixinUtil.initPreviewMessage(media_id)).toString();

        int result = WeixinUtil.previewMessage(TokenThread.accessToken.getToken(),groupMessage);

        if(result==0){
            System.out.println("发送成功");
        }else {
            System.out.println("错误码" + result);
        }

        return result;

    }


}












 /*   public String sendGroupMessage(){

        String groupUrl = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";//ACCESS_TOKEN是获取到的access_token，根据分组id发群发消息地址
        String groupUrl1 = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=NkwaEYtrewLfkgMphjwUmnZ0l6vguB794RBhstvHUT58nLk2HMrQ3Ji9EJTD8aOyViteTyV6H1xrVLx2Myee1MUdwz0xEcw0gmlBpf6VkFoMQRfAAAQUV";//根据openid发群发消息地址
        String group1data = "{\"filter\":{\"is_to_all\":false,\"group_id\":\"2\"},\"text\":{\"content\":\"群发消息测试\"},\"msgtype\":\"text\"}\";";
        String openid1data = "{\"touser\":[\"obGXiwHTGN_4HkR2WToFj_3uaEKY\",\"obGXiwNu0z2o_RRWaODvaZctdWEM\"],\"msgtype\": \"text\",\"text\": {\"content\": \"测试文本消息\"}}";
        String openid2data = "{\"touser\":[\"obGXiwHTGN_4HkR2WToFj_3uaEKY\",\"obGXiwNu0z2o_RRWaODvaZctdWEM\"], \"voice\": {\"media_id\":\"UfMRvSiXAD5_iUS8u0Gc3JrKGWOABE9ivQbgrX6i-mVrKGBRL9KnKlioK1BxTPc3\"},\"msgtype\":\"voice\"}";
        String openid3data = "{\"touser\":[\"obGXiwHTGN_4HkR2WToFj_3uaEKY\",\"obGXiwNu0z2o_RRWaODvaZctdWEM\"], \"image\": {\"media_id\":\"fNUzGbYzTRui4N7-eyx9e3viP8uJuzztAvA32lIdjX4Cucj7mGN_1jpWjn7O80c8\"},\"msgtype\":\"image\"}";
        String openid4data = "{\"touser\":[\"obGXiwHTGN_4HkR2WToFj_3uaEKY\",\"obGXiwNu0z2o_RRWaODvaZctdWEM\"], \"mpnews\": {\"media_id\":\"6I8DOB-7rJsY_zdOCe6YJKJ59MwXWPb2iYBKVqb22cBHPtECYdRgiWIULfCW-hcF\"},\"msgtype\":\"mpnews\"}";
        JSONObject json = CommUtil.httpRequest(groupUrl1, "POST", openid4data);
        return json.toString();

    }*/