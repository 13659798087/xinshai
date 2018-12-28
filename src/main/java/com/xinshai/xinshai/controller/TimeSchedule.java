package com.xinshai.xinshai.controller;

import com.xinshai.xinshai.entiry.ListResult;
import com.xinshai.xinshai.entiry.TemplateData;
import com.xinshai.xinshai.entiry.WechatTemplate;
import com.xinshai.xinshai.model.*;
import com.xinshai.xinshai.services.*;
import com.xinshai.xinshai.servlet.TokenThread;
import com.xinshai.xinshai.util.Guid;
import com.xinshai.xinshai.util.WeixinUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class TimeSchedule {

    @Resource
    private WeixinUserInfoServices weixinUserInfoServices;

    @Resource
    private MessageServices messageServices;

    @Resource
    private TemplateServices templateServices;

    @Resource
    private ResultPushMsgServices resultPushMsgServices;

    @Resource
    private HospitalServices hospitalServices;

    //绑定消息提示天数id
    @Value("${dayConutId}")
    private Integer dayConutId;

    //绑定消息提示次数id
    @Value("${bindRemindId}")
    private Integer bindRemindId;

    //验证不成功天数id
    @Value("${dayValidateId}")
    private Integer dayValidateId;

    //配置推送消息超过3次就不再推送
    @Value("${pushCountId}")
    private Integer pushCountId;

    //推送模板,提醒关注用户绑定个人信息
    @Value("${template1}")
    private String template1;

    //个人信息验证通过模板
    @Value("${template2}")
    private String template2;

    //报告结果推送模板
    @Value("${template4}")
    private String template4;

    //将未绑定个人信息的微信用户的信息插入resultPushMsg推送结果表
    //@Scheduled(cron = "${jobs.insertBindRemind}")
    public void insertBindRemind(){
        //查询关注时间>=7天，且未绑定的，且推送次数<3次的,这里先不考虑推送次数，先将推送结果写到推送结果表里
        List<WeixinUserInfo> result = null;
        int bindRemind = messageServices.getDayCount(bindRemindId); //三次
        int dayCount = messageServices.getDayCount(dayConutId); //7天未绑定就提醒

        result = weixinUserInfoServices.getRecentlyUser(dayCount,bindRemind);

        //只查询7天未关注的用户
        //result = weixinUserInfoServices.getConfigDay(dayCount);

        Template t = templateServices.getById(template1);
        StringBuilder noBindingStr1 = new StringBuilder();

        for(WeixinUserInfo r : result){
            r.setId(Guid.GenerateGUID());
            r.setTemplateId(template1);
            r.setFirst(t.getFirst());
            r.setKeyword1(t.getKeyword1());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            r.setKeyword2( sdf.format( new Date() ) );
            r.setFlag(0);
            r.setType(1);
            r.setRemark(t.getRemark());
            noBindingStr1.append( "'" + r.getOpenid() + "',");
        }
        Boolean isSuccess = false;
        //批量插入
        if(result.size()>0){
            isSuccess = resultPushMsgServices.insertBatchCode(result);//将推送成功的ResultPushMsg表的信息插入到pushHistory表
            String successId = "(" + noBindingStr1.substring(0,noBindingStr1.length()-1) + ")" ; //关注未绑定用户id的集合
            weixinUserInfoServices.updatePushCount(successId); //推送成功后删掉
        }

    }


    /**
     * 推送消息成功之后将改记录移动到pushHistory且删除该记录，推送失败的话就要将flag加1，超过三次就也移动到已推送的表中
     * 要增加推送失败执行的代码，也就是将flag加1，（可以将自己的微信取消绑定，或根据推送返回的错误码判断是否推送成功）
     */
    //@Scheduled(cron = "${jobs.timePush}")
    public void timePush(){

        List<ListResult> result = null;
        List<ListResult> result1 = new ArrayList<ListResult>();//要推送的list集合
        List<ListResult> result2 = new ArrayList<ListResult>();//推送成功的
        List<ListResult> result3 = new ArrayList<ListResult>();//推送成功的

        StringBuilder addStr = new StringBuilder();
        StringBuilder successDelete = new StringBuilder();
        StringBuilder failDeleteId = new StringBuilder();

        int dayCount = messageServices.getDayCount(pushCountId); //查询发送错误次数超过三次的，就删掉不再推送

        result = resultPushMsgServices.getPushResult();//查询出表的所有要推送的信息

        for(ListResult r : result){
            if( r.getFlag() < dayCount ){
                result1.add(r); //继续推送的
            }else{
                result2.add(r); //不再推送的
            }
        }

        for(ListResult r : result1){
            //调用发送模板消息接口，成功返回0，且将该记录插入pushHistory表，删除该记录，失败的话将flag加1，
            int success = 0;
            if(template1.equals(r.getTid())){//模板1，推送绑定个人信息的模板
                // 封装基础数据
                WechatTemplate wechatTemplate = new WechatTemplate();
                wechatTemplate.setTemplate_id( r.getTemplateId() );
                wechatTemplate.setTouser( r.getOpenId() );
                wechatTemplate.setUrl( hospitalServices.getDomainUrl() + "/personalData/personal");
                Map<String,TemplateData> mapdata = new HashMap();
                // 封装模板数据
                TemplateData first = new TemplateData();
                first.setValue( r.getFirst() );
                mapdata.put("first", first);

                TemplateData keyword1 = new TemplateData();
                keyword1.setValue( r.getKeyword1() );
                mapdata.put("keyword1", keyword1);

                TemplateData keyword2 = new TemplateData();
                keyword2.setValue(r.getKeyword2());
                mapdata.put("keyword2", keyword2);

                wechatTemplate.setData(mapdata);
                String msg  = JSONObject.fromObject(wechatTemplate).toString();

                success = WeixinUtil.sendTemplatesMsg(TokenThread.accessToken.getToken(),msg);
            }
            if(template2.equals(r.getTid())){//审核通过
                // 封装基础数据
                WechatTemplate wechatTemplate = new WechatTemplate();
                wechatTemplate.setTemplate_id( r.getTemplateId() );
                wechatTemplate.setTouser( r.getOpenId() );
                wechatTemplate.setUrl( hospitalServices.getDomainUrl() + "/personalData/personal");
                Map<String,TemplateData> mapdata = new HashMap();
                // 封装模板数据
                TemplateData first = new TemplateData();
                first.setValue( r.getFirst() );
                mapdata.put("first", first);

                TemplateData keyword1 = new TemplateData();
                keyword1.setValue( r.getKeyword1() );
                mapdata.put("keyword1", keyword1);

                TemplateData keyword2 = new TemplateData();
                keyword2.setValue(r.getKeyword2());
                mapdata.put("keyword2", keyword2);

                TemplateData remark = new TemplateData();
                remark.setValue(r.getRemark() );
                mapdata.put("remark", remark);

                wechatTemplate.setData(mapdata);
                String msg  = JSONObject.fromObject(wechatTemplate).toString();

                success = WeixinUtil.sendTemplatesMsg(TokenThread.accessToken.getToken(),msg);
            }
            if(template4.equals(r.getTid())){//报告结果推送

                // 封装基础数据
                WechatTemplate wechatTemplate = new WechatTemplate();
                wechatTemplate.setTemplate_id( r.getTemplateId() );
                wechatTemplate.setTouser( r.getOpenId() );

                //可能有多张报表
                wechatTemplate.setUrl( hospitalServices.getDomainUrl() + "reportQuery/personReport?patientId="+r.getPatientId());//报告结果url

                Map<String,TemplateData> mapdata = new HashMap();
                // 封装模板数据
                TemplateData first = new TemplateData();
                first.setValue( r.getFirst() );
                mapdata.put("first", first);

                TemplateData keyword1 = new TemplateData();
                keyword1.setValue( r.getKeyword1() );
                mapdata.put("keyword1", keyword1);

                TemplateData keyword2 = new TemplateData();
                keyword2.setValue(r.getKeyword2());
                mapdata.put("keyword2", keyword2);

                TemplateData keyword3 = new TemplateData();
                keyword3.setValue( r.getKeyword3() );
                mapdata.put("keyword3", keyword3);

                TemplateData keyword4 = new TemplateData();
                keyword4.setValue(r.getKeyword4());
                mapdata.put("keyword4", keyword4);

                TemplateData remark = new TemplateData();
                remark.setValue( r.getRemark() );
                mapdata.put("remark", remark);


                wechatTemplate.setData(mapdata);
                String msg  = JSONObject.fromObject(wechatTemplate).toString();
                success = WeixinUtil.sendTemplatesMsg(TokenThread.accessToken.getToken(),msg);
            }

            if(success==0){//说明推送成功
                r.setFlag(r.getFlag()+1);
                result3.add(r);//推送成功的集合
                successDelete.append( "'" + r.getId() + "',");//推送成功的id集合
            }else{//消息推送失败
                addStr.append( "'" + r.getId() + "'," );
            }
        }

        if( result3.size() > 0 ){

            resultPushMsgServices.insertBatchHistory(result3,1);//将flag+1和success=1,两个字段更改，推送成功的ResultPushMsg表的信息插入到pushHistory

            String successId = "(" + successDelete.substring(0,successDelete.length()-1) + ")" ; //推送成功的记录的id的集合
            //推送成功之后，将flag加1
            resultPushMsgServices.deleteSuccessId(successId); //推送成功后删掉
        }

        if( !StringUtils.isEmpty( addStr.toString() ) ){
            String wrongId = "(" + addStr.substring(0,addStr.length()-1) + ")" ; //推送错误的记录的id的集合
            resultPushMsgServices.pushFail(wrongId);//将推送失败的记录的success标识改为2（失败），flag加1
        }

        if( result2.size()>0 ){//推送超过三次的记录，就直接插入推送历史表，且删掉推送表的记录
            resultPushMsgServices.insertBatchHistory(result2,2);

            for(ListResult r : result2){
                failDeleteId.append( "'" + r.getId() + "',");
            }
            String failId = "(" + failDeleteId.substring(0,failDeleteId.length()-1) + ")" ; //推送成功的记录的id的集合
            resultPushMsgServices.deleteSuccessId(failId); //发送失败后超过3此就将记录删掉
        }

    }


    /*@Scheduled(cron = "${jobs.insertBindRemind}")
    public void insertBindRemind() {
        new Thread(new TimeBindRemind()).start();
    }*/

}








