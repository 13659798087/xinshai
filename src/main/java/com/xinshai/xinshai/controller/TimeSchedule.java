package com.xinshai.xinshai.controller;

import com.xinshai.xinshai.entiry.ListResult;
import com.xinshai.xinshai.entiry.TemplateData;
import com.xinshai.xinshai.entiry.WechatTemplate;
import com.xinshai.xinshai.model.*;
import com.xinshai.xinshai.services.*;
import com.xinshai.xinshai.servlet.TokenThread;
import com.xinshai.xinshai.util.DomainUrl;
import com.xinshai.xinshai.util.Guid;
import com.xinshai.xinshai.util.WeixinUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class TimeSchedule {

    @Resource
    private WeixinUserInfoServices weixinUserInfoServices;

    @Resource
    private PatientsServices patientsServices;

    @Resource
    private MessageServices messageServices;

    @Resource
    private TemplateServices templateServices;

    @Resource
    private ResultPushMsgServices resultPushMsgServices;


    //绑定消息提示天数id
    @Value("${dayConutId}")
    private Integer dayConutId;

    //验证不成功天数id
    @Value("${dayValidateId}")
    private Integer dayValidateId;

    //配置推送消息超过3次就不再推送
    @Value("${pushCountId}")
    private Integer pushCountId;

    @Value("${template1}")
    private String template1;

    @Value("${template3}")
    private String template3;


    //将未绑定个人信息的微信用户的信息插入resultPushMsg推送结果表
    @Scheduled(cron = "${jobs.insertBindRemind}")
    public void insertBindRemind() {
        List<WeixinUserInfo> result = null;
        int dayCount = messageServices.getDayCount(dayConutId);
        result = weixinUserInfoServices.getRecentlyUser(dayCount);
        for(WeixinUserInfo r : result){
            resultPushMsgServices.insertBindRemind(Guid.GenerateGUID(),r.getOpenid(),template1,1);
        }
    }

    /**
     * 推送消息成功之后将改记录移动alread且删除该记录，推送失败的话就要将flag加1，超过三次就也移动到已推送的表中
     * 要增加推送失败执行的代码，也就是将flag加1，（可以将自己的微信取消绑定，或根据推送返回的错误码判断是否推送成功）
     */
    @Scheduled(cron = "${jobs.timePush}")
    public void timePush(){
        List<ListResult> result = null;
        //查询关注7天的且没绑定信息的用户，也就是concerns=0（关注），binding=1（未绑定过），
        result = resultPushMsgServices.getPushResult(pushCountId);

        for(ListResult r : result){
            if(1 == r.getType()){
                // 封装基础数据
                WechatTemplate wechatTemplate = new WechatTemplate();
                wechatTemplate.setTemplate_id( r.getTemplateId() );
                wechatTemplate.setTouser( r.getOpenId() );
                wechatTemplate.setUrl( DomainUrl.getUrl()+"/personalData/personal");
                Map<String,TemplateData> mapdata = new HashMap<>();
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
                WeixinUtil.sendTemplatesMsg(TokenThread.accessToken.getToken(),msg);
            }
            if(2 == r.getType()){
                // 封装基础数据
                WechatTemplate wechatTemplate = new WechatTemplate();
                wechatTemplate.setTemplate_id( r.getTemplateId() );
                wechatTemplate.setTouser( r.getOpenId() );
                wechatTemplate.setUrl( DomainUrl.getUrl()+"/personalData/personal");
                Map<String,TemplateData> mapdata = new HashMap<>();
                // 封装模板数据
                TemplateData first = new TemplateData();
                TemplateData keyword1 = new TemplateData();
                //first.setValue( r.getFirst() );
                if(r.getIsPass()==0){
                    first.setValue( r.getFirst().split("；")[0] );
                    keyword1.setValue( r.getKeyword1().split("；")[0] );
                }
                if(r.getIsPass()==2){
                    first.setValue( r.getFirst().split("；")[1] );
                    keyword1.setValue( r.getKeyword1().split("；")[1] );
                }
                mapdata.put("first", first);

                //TemplateData keyword1 = new TemplateData();
                //keyword1.setValue( r.getKeyword1() );
                mapdata.put("keyword1", keyword1);

                TemplateData keyword2 = new TemplateData();
                keyword2.setValue(r.getKeyword2());
                mapdata.put("keyword2", keyword2);

                wechatTemplate.setData(mapdata);
                String msg  = JSONObject.fromObject(wechatTemplate).toString();
                WeixinUtil.sendTemplatesMsg(TokenThread.accessToken.getToken(),msg);
            }
            if(3 == r.getType()){
                // 封装基础数据
                WechatTemplate wechatTemplate = new WechatTemplate();
                wechatTemplate.setTemplate_id( r.getTemplateId() );
                wechatTemplate.setTouser( r.getOpenId() );
                wechatTemplate.setUrl( DomainUrl.getUrl()+"/personalData/personal");

                //wechatTemplate.setUrl( DomainUrl.getUrl() + "reportQuery/reportPreview?c_id="+c_id+"&c_code="+c_code+"&authorHospital="+authorHospital);//报告结果url


                Map<String,TemplateData> mapdata = new HashMap<>();
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
                WeixinUtil.sendTemplatesMsg(TokenThread.accessToken.getToken(),msg);
            }

        }
    }




    //定时查询，查询personBinding表中状态为1，即没验证通过的用户信息，还有就是（当前时间-创建时间）小于10天
    @Scheduled(cron = "${jobs.validate}")
    public void TenAndNoPass(){

        int dayCount = messageServices.getDayCount(dayValidateId);

        //查询创建时间超过10天的记录（相对创建时间），没通过的验证的记录且没推送过信息的记录
        List<PersonBinding> lsitPersonBinding = patientsServices.getTenAndNoPass(dayCount);

        String templateId = templateServices.getById(template3).getTemplateId();

        for(PersonBinding p : lsitPersonBinding){
                int j = patientsServices.updatePass(p.getId());//标识改为0已推送消息过消息，下次不再推送

                // 封装基础数据
                WechatTemplate wechatTemplate = new WechatTemplate();
                wechatTemplate.setTemplate_id( templateId );
                wechatTemplate.setTouser( p.getOpenId() );
                wechatTemplate.setUrl( DomainUrl.getUrl()+"/personalData/personal");
                Map<String,TemplateData> mapdata = new HashMap<>();
                // 封装模板数据
                TemplateData first = new TemplateData();
                first.setValue("您好！");
                //first.setColor("black");
                mapdata.put("first", first);

                TemplateData keyword1 = new TemplateData();
                keyword1.setValue( "您绑定的消息验证不通过" );
                //first.setColor("#173177");
                mapdata.put("keyword1", keyword1);

                TemplateData keyword2 = new TemplateData();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String format = formatter.format(new Date());
                keyword2.setValue(format);
                //first.setColor("#173177");
                mapdata.put("keyword2", keyword2);

                wechatTemplate.setData(mapdata);
                String msg  = JSONObject.fromObject(wechatTemplate).toString();
                WeixinUtil.sendTemplatesMsg(TokenThread.accessToken.getToken(),msg);
        }

        //查询10天内的记录通过的记录，ispass=0，验证通过
        List<PersonBinding> lsitPersonBinding2 = patientsServices.getTenAndNoPass2(dayCount);
        for(PersonBinding p : lsitPersonBinding2){
            //int j = patientsServices.updatePass2(p.getId());

            // 封装基础数据
            WechatTemplate wechatTemplate = new WechatTemplate();
            wechatTemplate.setTemplate_id( templateId );
            wechatTemplate.setTouser( p.getOpenId() );
            wechatTemplate.setUrl( DomainUrl.getUrl()+"/personalData/personal");
            Map<String,TemplateData> mapdata = new HashMap<>();
            // 封装模板数据
            TemplateData first = new TemplateData();
            first.setValue("您好！");
            //first.setColor("black");
            mapdata.put("first", first);

            TemplateData keyword1 = new TemplateData();
            keyword1.setValue( "您绑定的消息验证已通过" );
            //first.setColor("#173177");
            mapdata.put("keyword1", keyword1);

            TemplateData keyword2 = new TemplateData();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String format = formatter.format(new Date());
            keyword2.setValue(format);
            //first.setColor("#173177");
            mapdata.put("keyword2", keyword2);

            wechatTemplate.setData(mapdata);
            String msg  = JSONObject.fromObject(wechatTemplate).toString();
            WeixinUtil.sendTemplatesMsg(TokenThread.accessToken.getToken(),msg);

           //消息推送，验证不通过
        }

    }

    //定时任务  发送模板消息,报告结果推送
    @Scheduled(cron = "${jobs.reportResult}")
    public void test1(){
        //查询ResultPushMsg表的信息，此数据是内网出报告的插入的相关信息
        List<ResultPushMsg> resultList = weixinUserInfoServices.getAllMsg();
        String value = "";

        /*for(ResultPushMsg r : resultList){
            // 封装基础数据
            WechatTemplate wechatTemplate = new WechatTemplate();
            wechatTemplate.setTemplate_id(r.getTemplateId());
            wechatTemplate.setTouser( r.getOpenId() );
            String c_id = r.getC_id();
            String c_code = r.getC_code() ;
            String authorHospital = r.getAuthorHospital();

            wechatTemplate.setUrl( DomainUrl.getUrl() + "reportQuery/reportPreview?c_id="+c_id+"&c_code="+c_code+"&authorHospital="+authorHospital);//报告结果url

            Map<String,TemplateData> mapdata = new HashMap<>();
            // 封装模板数据
            TemplateData first = new TemplateData();
            first.setValue("报告结果");
            //first.setColor("#173177");
            mapdata.put("first", first);

            TemplateData keyword1 = new TemplateData();
            keyword1.setValue( r.getP_m_name() );
            //first.setColor("#173177");
            mapdata.put("keyword1", keyword1);

            TemplateData keyword2 = new TemplateData();
            keyword2.setValue( r.getCombineName() );
            //first.setColor("#173177");
            mapdata.put("keyword2", keyword2);

            TemplateData keyword3 = new TemplateData();
            keyword3.setValue( r.getReportType() );
            //first.setColor("#173177");
            mapdata.put("keyword3", keyword3);

            TemplateData keyword4 = new TemplateData();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String format = formatter.format(new Date());
            keyword4.setValue(format);
            mapdata.put("keyword4", keyword4);

            wechatTemplate.setData(mapdata);
            String msg  = JSONObject.fromObject(wechatTemplate).toString();
            WeixinUtil.sendTemplatesMsg(TokenThread.accessToken.getToken(),msg);

            String str = "('" + r.getId()+"','"+r.getOpenId()+"','"+r.getBloodCard()+"','"+r.getP_m_name()+"','"
                    +r.getCombineName()+"','"+r.getReportDate()+"','"+r.getReportType()+"','"+r.getTemplateId() + "'),";

            value+=str;
        }
*/

        //String a = value.substring(0,value.length()-1);
        //weixinUserInfoServices.AlreadyPushMsg(a);//ResultPushMsg表的信息插入到alreadyPushMsg表
        //weixinUserInfoServices.deleteResult();
    }





    /*TemplateData keyword2 = new TemplateData();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    String format = formatter.format(new Date());
    keyword2.setValue(format);
    first.setColor("#173177");
    mapdata.put("keyword2", keyword2);*/

}








