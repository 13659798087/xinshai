package com.xinshai.xinshai.controller;

import com.xinshai.xinshai.entiry.*;
import com.xinshai.xinshai.services.HospitalServices;
import com.xinshai.xinshai.services.ResultPushMsgServices;
import com.xinshai.xinshai.servlet.TokenThread;
import com.xinshai.xinshai.util.Guid;
import com.xinshai.xinshai.util.Paging;
import com.xinshai.xinshai.util.WeixinUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/resultPushMsg")
public class ResultPushMsgController {

    @Resource
    private ResultPushMsgServices ResultPushMsgServices;

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
    @Value("4")
    private String template4;


    private String view = "resultPushMsg/";

    @RequestMapping("/resultPushMsg")
    public String resultPushMsg(){
        return view+"resultPushMsg";
    }

    @ResponseBody
    @RequestMapping("/waitSendMsg")
    public Map<String,Object> getReplyMsg(String pageNumber,String rowNumber,String sortName,
                     String sortOrder,String p_name,String first,String type,HttpServletRequest request){

        List<WaitPushMsg> listWaitSendMsg = new ArrayList<WaitPushMsg>();

        int pageNo = Integer.parseInt((pageNumber == null || pageNumber =="0") ? "1":pageNumber);
        int pageSize = Integer.parseInt((rowNumber == null || rowNumber =="0") ? "10":rowNumber);
        String orderBy = sortName == null  ? "updaterTime":sortName;
        String order = sortOrder == null  ? PageResults.DESC:sortOrder;

        PageResults<WaitPushMsg> pageResults = new PageResults<WaitPushMsg>();
        pageResults.setPageNo(pageNo);
        pageResults.setPageSize(pageSize);
        pageResults.setOrderBy(orderBy);
        pageResults.setOrder(order);

        listWaitSendMsg = ResultPushMsgServices.waitSendMsg((pageNo-1)*pageSize+1, pageNo*pageSize,p_name,first,type);

        long totalCount = ResultPushMsgServices.waitSendMsgCount(p_name,first,type);

        pageResults.setResult(listWaitSendMsg);
        pageResults.setTotalCount(totalCount);

        return Paging.ajaxGrid(pageResults);

    }

    @ResponseBody
    @RequestMapping("/deleteSendMsg")
    public void deleteSendMsg(String id){
        ResultPushMsgServices.deleteSendMsg(id);
    }

    @RequestMapping("/createEditMsg")
    public String createEditMsg(String id,String openId,String patientId,String tid,String first,String keyword1,
                            String keyword2,String keyword3,String keyword4,String keyword5,String remark){

        String sign = "";
        if(!StringUtils.isEmpty(id)){  //修改
            ResultPushMsgServices.updateMsg(id,openId,patientId,tid,first,keyword1,keyword2,keyword3,keyword4,keyword5,remark);
            sign = "edit";
        }else{  //新增
            id = Guid.GenerateGUID();
            ResultPushMsgServices.creatMsg(id,openId,patientId,tid,first,keyword1,keyword2,keyword3,keyword4,keyword5,remark);
            sign = "add";
        }

        return "redirect:addPushMsg?sign="+sign; //重定向
    }

    @RequestMapping("/addPushMsg")
    public String addPushMsg(Model model,String id,String openId,String patientId,String tid,String first,String keyword1,
              String keyword2, String keyword3,String keyword4,String keyword5,String remark,String sign){

        model.addAttribute("id",id);
        model.addAttribute("openId",openId);
        model.addAttribute("patientId",patientId);
        model.addAttribute("tid",tid);
        model.addAttribute("first",first);
        model.addAttribute("keyword1",keyword1);
        model.addAttribute("keyword2",keyword2);
        model.addAttribute("keyword3",keyword3);
        model.addAttribute("keyword4",keyword4);
        model.addAttribute("keyword5",keyword5);
        model.addAttribute("remark",remark);

        model.addAttribute("sign",sign);//标识是修改还是编辑
        return view+"addPushMsg";
    }

    /**
     * 推送消息成功之后将改记录移动到pushHistory且删除该记录，推送失败的话就要将flag加1，超过三次就也移动到已推送的表中
     * 要增加推送失败执行的代码，也就是将flag加1，（可以将自己的微信取消绑定，或根据推送返回的错误码判断是否推送成功）
     */
    @ResponseBody
    @RequestMapping("/handPush")
    public void timePush(String id){

        List<ListResult> result = new ArrayList<ListResult>();//查询出选中要推送的list集合

        StringBuilder addStr = new StringBuilder();//要推送的id集合，先查询出列表

        StringBuilder failId = new StringBuilder();//要推送的id集合，先查询出列表

        String[] list = id.split(",");

        for(int i = 0; i < list.length; i++){
            addStr.append( "'" + list[i] + "'," );
        }
        String listId = "(" + addStr.substring(0,addStr.length()-1) + ")" ;

        result = resultPushMsgServices.getListId(listId);//要推送的list集合

        List<ListResult> result1 = new ArrayList<ListResult>(); //推送成功的集合

        StringBuilder successDelete = new StringBuilder();//推送成功i集合

        //StringBuilder listPatientId = new StringBuilder();

        for(ListResult r : result){
            //调用发送模板消息接口，成功返回0，且将该记录插入pushHistory表，删除该记录，失败的话将flag加1，
            int success = 0;
            if(template1.equals(r.getTid())){//模板1，推送绑定个人信息的模板
                // 封装基础数据
                WechatTemplate wechatTemplate = new WechatTemplate();
                wechatTemplate.setTemplate_id( r.getTemplateId() );
                wechatTemplate.setTouser( r.getOpenId() );
                wechatTemplate.setUrl( hospitalServices.getDomainUrl()  +"/personalData/personal");
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
                result1.add(r);//推送成功的集合
                successDelete.append( "'" + r.getId() + "',");//推送成功的id集合
            }else{//消息推送失败
                failId.append( "'" + r.getId() + "'," );//推送失败的id集合
            }
        }

        if( result1.size() > 0 ){//推送成功的记录
            resultPushMsgServices.insertBatchHistory(result1,1);//将flag+1和success=1,两个字段更改，推送成功的ResultPushMsg表的信息插入到pushHistory
            String successId = "(" + successDelete.substring(0,successDelete.length()-1) + ")" ; //推送成功的记录的id的集合
            //推送成功之后，将flag加1
            resultPushMsgServices.deleteSuccessId(successId); //推送成功后删掉

        }

        if( !StringUtils.isEmpty( failId.toString() ) ){
            String wrongId = "(" + failId.substring(0,failId.length()-1) + ")" ; //推送错误的记录的id的集合
            resultPushMsgServices.pushFail(wrongId);//将推送失败的记录的success标识改为2（失败），flag加1
        }

    }


}
