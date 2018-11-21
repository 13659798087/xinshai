package com.xinshai.xinshai.controller;

import com.xinshai.xinshai.entiry.PageResults;
import com.xinshai.xinshai.model.MessagePush;
import com.xinshai.xinshai.services.MessageServices;
import com.xinshai.xinshai.util.Guid;
import com.xinshai.xinshai.util.Paging;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/open/message")
public class MessageConroller1 {

    private String view = "message/";

    @Resource
    private MessageServices messageServices;

    /**
     * 定时消息推送在TimeSchedule类
     * @return
     */

    @RequestMapping("/getMessage")
    public String getMessage(){
        return view + "getMessage";
    }

    @RequestMapping("/messagePush")
    public String messagePush(){
        return view + "messagePush";
    }

    @ResponseBody
    @RequestMapping("/getPushConfig")
    public Map<String,Object> getPushConfig(String pageNumber, String rowNumber, String sortName,
                                          String sortOrder, String meaning){


        List<MessagePush> userInfo = new ArrayList<MessagePush>();

        int pageNo = Integer.parseInt((pageNumber == null || pageNumber =="0") ? "1":pageNumber);
        int pageSize = Integer.parseInt((rowNumber == null || rowNumber =="0") ? "10":rowNumber);
        String orderBy = sortName == null  ? "updaterTime":sortName;
        String order = sortOrder == null  ? PageResults.DESC:sortOrder;

        PageResults<MessagePush> pageResults = new PageResults<MessagePush>();
        pageResults.setPageNo(pageNo);
        pageResults.setPageSize(pageSize);
        pageResults.setOrderBy(orderBy);
        pageResults.setOrder(order);

        userInfo = messageServices.getPushConfig((pageNo-1)*pageSize+1, pageNo*pageSize,meaning);

        long totalCount = messageServices.getPushConfigCount(meaning);

        pageResults.setResult(userInfo);
        pageResults.setTotalCount(totalCount);

        return Paging.ajaxGrid(pageResults);
    }

    @RequestMapping("/addMessagePush")
    public String addMessagePush(Model model,String id,String meaning,String dayCount,String type,String sign){

        model.addAttribute("id",id);
        model.addAttribute("meaning",meaning);
        model.addAttribute("dayCount",dayCount);

        model.addAttribute("type",type);
        model.addAttribute("sign",sign);//标识是修改还是编辑
        return view+"addMessagePush";
    }

    @RequestMapping("/createPush")
    public String creatUser(String id,String meaning,String dayCount,String type){
        String sign = "";
        if("e".equals(type)){//修改
            messageServices.updatePush(id,meaning,dayCount);
            sign = "edit";

        }else{//新增
            messageServices.createPush(Guid.GenerateGUID(),meaning,dayCount);
            sign = "add";
        }
        return "redirect:addMessagePush?type=a&sign="+sign; //重定向
    }

    @ResponseBody
    @RequestMapping("/deletePush")
    public void deletePush(String id){
        messageServices.deletePush(id);
    }





}
