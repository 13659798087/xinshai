/*
package com.xinshai.xinshai.servlet;

import com.xinshai.xinshai.model.Template;
import com.xinshai.xinshai.model.WeixinUserInfo;
import com.xinshai.xinshai.services.*;
import com.xinshai.xinshai.util.Guid;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class TimeBindRemind implements Runnable {
    @Resource
    private WeixinUserInfoServices weixinUserInfoServices;
    @Resource
    private MessageServices messageServices;
    @Resource
    private TemplateServices templateServices;
    @Resource
    private ResultPushMsgServices resultPushMsgServices;



    private static WeixinUserInfoServices weixinUserInfoServices;
    @Resource
    public void setVerificDao(WeixinUserInfoServices weixinUserInfoServices) {
        this.weixinUserInfoServices = weixinUserInfoServices;
    }

    private static MessageServices messageServices;
    @Resource
    public void setMessageServices(MessageServices messageServices) {
        this.messageServices = messageServices;
    }

    private static TemplateServices templateServices;
    @Resource
    public void setTemplateServices(TemplateServices templateServices) {
        this.templateServices = templateServices;
    }

    private static ResultPushMsgServices resultPushMsgServices;
    @Resource
    public void setResultPushMsgServices(ResultPushMsgServices resultPushMsgServices) {
        this.resultPushMsgServices = resultPushMsgServices;
    }

    private static Integer dayConutId;
    @Value("${dayConutId}")
    public void setDayConutId(Integer dayConutId) {
        this.dayConutId = dayConutId;
    }

    private static Integer bindRemindId;
    @Value("${bindRemindId}")
    public void setBindRemindId(Integer bindRemindId) {
        this.bindRemindId = bindRemindId;
    }

    private static Integer dayValidateId;
    @Value("${dayValidateId}")
    public void setDayValidateId(Integer dayValidateId) {
        this.dayValidateId = dayValidateId;
    }

    private static String template1;
    @Value("${template1}")
    public void setTemplate1(String template1) {
        this.template1 = template1;
    }

    //绑定消息提示天数id
@Value("${dayConutId}")
    private Integer dayConutId;
    //绑定消息提示次数id
    @Value("${bindRemindId}")
    private Integer bindRemindId;
    //验证不成功天数id
    @Value("${dayValidateId}")
    private Integer dayValidateId;
    //推送模板,提醒关注用户绑定个人信息
    @Value("${template1}")
    private String template1;


 @Scheduled(cron = "${jobs.insertBindRemind}")

    @Override
    public void run() {
        List<WeixinUserInfo> result1 = null;
        List<WeixinUserInfo> result2 = null;
        int bindRemind = messageServices.getDayCount(bindRemindId);
        int dayCount = messageServices.getDayCount(dayConutId);
        result1 = weixinUserInfoServices.getRecentlyUser(dayCount);

        Template t = templateServices.getById(template1);
        StringBuilder insertStr1 = new StringBuilder();
        StringBuilder noBindingStr1 = new StringBuilder();

        StringBuilder insertStr2 = new StringBuilder();
        StringBuilder noBindingStr2 = new StringBuilder();

        for(WeixinUserInfo r : result1){
            String str = "('" + Guid.GenerateGUID()+"','"+r.getOpenid()+"','"+""+"','"+template1+"','"
                    +t.getFirst()+"','"+t.getKeyword1()+"','"+t.getKeyword2()+"','"+t.getKeyword3() +"','"
                    +t.getKeyword4()+"','"+t.getKeyword5()+"','"+ new java.sql.Timestamp(System.currentTimeMillis()).toString()+"','"+0 +"','"
                    +1 + "'),";

            insertStr1.append(str);
            noBindingStr1.append( "'" + r.getOpenid() + "',");
        }
        if( !StringUtils.isEmpty( insertStr1.toString() ) ){
            String addInsert = insertStr1.substring(0,insertStr1.length()-1); //要插入推送历史表的数据
            resultPushMsgServices.insertBindRemind(addInsert);//将推送成功的ResultPushMsg表的信息插入到pushHistory表

            String successId = "(" + noBindingStr1.substring(0,noBindingStr1.length()-1) + ")" ; //关注未绑定用户id的集合
            weixinUserInfoServices.updatePushCount(successId); //推送成功后删掉
        }

        System.out.println("第（1）次插入绑定信息已完成----------:"+ new java.sql.Timestamp(System.currentTimeMillis()).toString());

        //每天9点推送，还不绑定的话12小时之后再次将该消息插入推送记录，但报告推送要九点之后，以免每天同时推送同一个人两天一样的绑定消息
        //Thread.sleep(7200 * 12 * 1000);//24小时

        try {
            Thread.sleep(120 * 1000);//睡两分钟
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("第（2）次插入绑定信息已开始----------:"+ new java.sql.Timestamp(System.currentTimeMillis()).toString());

        //查询关注的未绑定的推送过小于3次的用户
        result2 = weixinUserInfoServices.pushNoBing(bindRemind);
        for(WeixinUserInfo r : result2){
            String str = "('" + Guid.GenerateGUID()+"','"+r.getOpenid()+"','"+""+"','"+template1+"','"
                    +t.getFirst()+"','"+t.getKeyword1()+"','"+t.getKeyword2()+"','"+t.getKeyword3() +"','"
                    +t.getKeyword4()+"','"+t.getKeyword5()+"','"+ new java.sql.Timestamp(System.currentTimeMillis()).toString()+"','"+0 +"','"
                    +1 + "'),";

            insertStr2.append(str);
            noBindingStr2.append( "'" + r.getOpenid() + "',");
        }
        if( !StringUtils.isEmpty( insertStr2.toString() ) ){
            String addInsert = insertStr2.substring(0,insertStr2.length()-1); //要插入推送历史表的数据
            resultPushMsgServices.insertBindRemind(addInsert);//将推送成功的ResultPushMsg表的信息插入到pushHistory表

            String successId = "(" + noBindingStr2.substring(0,noBindingStr2.length()-1) + ")" ; //关注未绑定用户id的集合
            weixinUserInfoServices.updatePushCount(successId); //推送成功后删掉
        }
    }


}
*/
