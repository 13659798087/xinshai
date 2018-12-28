package com.xinshai.xinshai.controller;

import com.xinshai.xinshai.entiry.PageResults;
import com.xinshai.xinshai.model.AttentionReply;
import com.xinshai.xinshai.services.AttentionServices;
import com.xinshai.xinshai.util.Guid;
import com.xinshai.xinshai.util.Paging;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/open/attention")
public class AttentionController {

    private String view = "attention/";

    @Resource
    private AttentionServices attentionServices;

    @RequestMapping("/attentionReply")
    public String attentionReply(){
        return view+"attentionReply";
    }

    @ResponseBody
    @RequestMapping("/getReplyMsg")
    public Map<String,Object> getReplyMsg(String pageNumber, String rowNumber, String sortName,
                                          String sortOrder, String userName, String organizationName, HttpServletRequest request){

        List<AttentionReply> userInfo = new ArrayList<AttentionReply>();

        int pageNo = Integer.parseInt((pageNumber == null || pageNumber =="0") ? "1":pageNumber);
        int pageSize = Integer.parseInt((rowNumber == null || rowNumber =="0") ? "10":rowNumber);
        String orderBy = sortName == null  ? "updaterTime":sortName;
        String order = sortOrder == null  ? PageResults.DESC:sortOrder;

        PageResults<AttentionReply> pageResults = new PageResults<AttentionReply>();
        pageResults.setPageNo(pageNo);
        pageResults.setPageSize(pageSize);
        pageResults.setOrderBy(orderBy);
        pageResults.setOrder(order);

        userInfo = attentionServices.getAttentionReply();

        long totalCount = attentionServices.getReplyCount();

        pageResults.setResult(userInfo);
        pageResults.setTotalCount(totalCount);

        return Paging.ajaxGrid(pageResults);

    }

    @RequestMapping("/createReply")
    public String createReply(String id,String type,String content,String orderNum){
        String sign = "";
        //修改
        if("a".equals(type)){//新增
            id = Guid.GenerateGUID();
            attentionServices.createReply(id,content,orderNum);
            sign = "add";
        }if("e".equals(type)){//编辑
            attentionServices.updateReply(id,content,orderNum);
            sign = "edit";
        }
        return "redirect:addReply?type=a&sign="+sign; //重定向
    }

    @RequestMapping("/addReply")
    public String addReply(Model model,String id,String content,String orderNum,String type,String sign){
        model.addAttribute("id",id);
        model.addAttribute("content",content);
        model.addAttribute("orderNum",orderNum);
        model.addAttribute("type",type);
        model.addAttribute("sign",sign);//标识是修改还是编辑
        return view+"addReply";
    }

    @ResponseBody
    @RequestMapping("/deleteReply")
    public void deleteReply(String id){
        attentionServices.deleteReply(id);
    }

    @RequestMapping("/replyMenu")
    public String replyMenu(){
        List<AttentionReply> reply = new ArrayList<AttentionReply>();
        reply = attentionServices.getAttentionReply();
        StringBuffer sb = new StringBuffer();
        int i = 1;
        for(AttentionReply r : reply){
            sb.append( r.getContent()+"\n\n");
            i++;
        }
        return sb.toString();
    }


}
