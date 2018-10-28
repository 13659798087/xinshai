package com.xinshai.xinshai.controller;

import com.xinshai.xinshai.entiry.TemplateData;
import com.xinshai.xinshai.entiry.WechatTemplate;
import com.xinshai.xinshai.model.CheckinfoTest;
import com.xinshai.xinshai.model.WeixinMsg;
import com.xinshai.xinshai.services.WeixinUserInfoServices;
import com.xinshai.xinshai.servlet.TokenThread;
import com.xinshai.xinshai.util.DomainUrl;
import com.xinshai.xinshai.util.WeixinUtil;
import net.sf.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TimeSchedule {

    @Resource
    private WeixinUserInfoServices weixinUserInfoServices;

    /**
     *  定时任务  发送模板消息
     */
    @Scheduled(cron = "${jobs.schedule}")
    public void test1(){

        //模拟查询出结果的报告
        List<CheckinfoTest> listCheckinfo = weixinUserInfoServices.getTest();

        //weixinMsg 查询已绑定的个人信息的表的信息，再和今天出报告的记录对应，将消息推送给绑定的微信用户
        List<WeixinMsg> listWeixinMsg = weixinUserInfoServices.getAllMsg();

        for(CheckinfoTest a : listCheckinfo){
            String m_name = null;
            String openid = null;
            String combine = null;
            String title = null;

            for(WeixinMsg l:listWeixinMsg){
                if(a.getM_name().equals(l.getP_name()) && a.getTel().equals(l.getP_tel()) ){
                    m_name = l.getP_name();
                    openid = l.getOpenId();
                    combine = a.getCombine();
                    title = a.getTitle();
                }
            }

            // 封装基础数据
            WechatTemplate wechatTemplate = new WechatTemplate();
            wechatTemplate.setTemplate_id(DomainUrl.getTemplate_id1());
            wechatTemplate.setTouser( openid );
            wechatTemplate.setUrl("https://www.baidu.com/");
            Map<String,TemplateData> mapdata = new HashMap<>();
            // 封装模板数据
            TemplateData first = new TemplateData();
            first.setValue("您好，您收到一份报告结果");
            first.setColor("#173177");
            mapdata.put("first", first);

            TemplateData keyword1 = new TemplateData();
            keyword1.setValue( m_name );
            first.setColor("#173177");
            mapdata.put("keyword1", keyword1);

            TemplateData keyword2 = new TemplateData();
            keyword2.setValue(combine);
            first.setColor("#173177");
            mapdata.put("keyword2", keyword2);

            TemplateData keyword3 = new TemplateData();
            keyword3.setValue(title);
            first.setColor("#173177");
            mapdata.put("keyword3", keyword3);

            TemplateData keyword4 = new TemplateData();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String format = formatter.format(new Date());
            keyword4.setValue(format);
            first.setColor("#173177");
            mapdata.put("keyword4", keyword4);

            wechatTemplate.setData(mapdata);
            String msg  = JSONObject.fromObject(wechatTemplate).toString();
            WeixinUtil.sendTemplatesMsg(TokenThread.accessToken.getToken(),msg);

        }

    }




}








