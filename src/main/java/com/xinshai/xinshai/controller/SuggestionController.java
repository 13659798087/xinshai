package com.xinshai.xinshai.controller;

import com.xinshai.xinshai.entiry.PageResults;
import com.xinshai.xinshai.model.Combine;
import com.xinshai.xinshai.model.Suggestion;
import com.xinshai.xinshai.services.HospitalServices;
import com.xinshai.xinshai.services.SuggestionServices;
import com.xinshai.xinshai.util.Guid;
import com.xinshai.xinshai.util.Paging;
import com.xinshai.xinshai.util.WeixinUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/suggestion")
public class SuggestionController {
    @Resource
    private HospitalServices hospitalServices;
    @Resource
    private SuggestionServices suggestionServices;

    private String view = "suggestion/";

    @RequestMapping("/wxSuggestion")
    public String wxSuggestion(String ok, Model model) {
        model.addAttribute("ok",ok);
        return view+"wxSuggestion";
    }

    @RequestMapping("/pcSuggestion")
    public String pcSuggestion() {
        return view+"pcSuggestion";
    }

    @ResponseBody
    @RequestMapping("/pcTableSuggestion")
    public Map<String, Object> pcTableSuggestion(String pageNumber,String rowNumber,String sortName,String sortOrder,
                                               String time1,String time2){

        Timestamp time_1 = null;
        Timestamp time_2 = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(!org.apache.axis.utils.StringUtils.isEmpty(time1)){
            time_1 = Timestamp.valueOf(time1 +" 00:00:00");
        }
        if(!org.apache.axis.utils.StringUtils.isEmpty(time2)){
            time_2 = Timestamp.valueOf(time2 +" 23:59:59");
        }

        List<Suggestion> listSuggestion = new ArrayList<Suggestion>();

        int pageNo = Integer.parseInt((pageNumber == null || pageNumber =="0") ? "1":pageNumber);
        int pageSize = Integer.parseInt((rowNumber == null || rowNumber =="0") ? "10":rowNumber);
        String orderBy = sortName == null  ? "updaterTime":sortName;
        String order = sortOrder == null  ? PageResults.DESC:sortOrder;

        PageResults<Suggestion> pageResults = new PageResults<Suggestion>();
        pageResults.setPageNo(pageNo);
        pageResults.setPageSize(pageSize);
        pageResults.setOrderBy(orderBy);
        pageResults.setOrder(order);

        listSuggestion = suggestionServices.getSuggestionList(time_1,time_2,(pageNo-1)*pageSize+1, pageNo*pageSize);

        long totalCount = suggestionServices.getSuggestionCount(time_1,time_2);
        pageResults.setResult(listSuggestion);
        pageResults.setTotalCount(totalCount);

        return Paging.ajaxGrid(pageResults);
    }

    @RequestMapping("/wxgetSuggestion")
    public String wxgetSuggestion(Model model,String openid) {
        List<Map> list = suggestionServices.wxgetSuggestion(openid);
        model.addAttribute("list",list);
        model.addAttribute("listSize",list.size());
        return view+"wxgetSuggestion";
    }

    @RequestMapping("/wxsubmitSuggestion")
    public void wxsubmitSuggestion( HttpServletResponse response,String name,String phone,String mail,String suggestion) throws IOException {
        String param = "&name="+name+"&phone="+phone+"&suggestion="+suggestion;
        if(!StringUtils.isEmpty(mail)){
            param += "&mail="+mail;
        }
        String id = Guid.GenerateGUID();
        String redirect_uri= URLEncoder.encode(hospitalServices.getDomainUrl() + "suggestion/toSuggestion?id="+id+param, "UTF-8");

        //简单获取openid的话参数response_type与scope与state参数固定写死即可
        StringBuffer url=new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize?redirect_uri="+redirect_uri+"&appid="+hospitalServices.getAppid()+"&response_type=code&scope=snsapi_base&state=1#wechat_redirect");
        response.sendRedirect(url.toString());//这里请不要使用get请求单纯的将页面跳转到该url即可
    }

    @RequestMapping("/toSuggestion")
    public String toSuggestion(HttpServletRequest request,String id,String name,String phone,String mail,String suggestion) {
        String code = request.getParameter("code");//微信活返回code值，用code获取openid
        String openid = WeixinUtil.getOpendId(code);
        suggestionServices.wxsubmitSuggestion(id,openid,name,phone,mail,suggestion);
        //return "redirect:wxSuggestion?ok=success";
        return "redirect:wxgetSuggestion?openid="+openid;
    }

    @ResponseBody
    @RequestMapping("/deleteSug")
    public void deleteSug(String id) {
        suggestionServices.deleteSug(id);
    }

    @RequestMapping("/wxgetSuggestion1")
    public void wxgetSuggestion1(HttpServletResponse response) throws IOException {
        String redirect_uri= URLEncoder.encode(hospitalServices.getDomainUrl() + "suggestion/toSuggestion1", "UTF-8");
        //简单获取openid的话参数response_type与scope与state参数固定写死即可
        StringBuffer url=new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize?redirect_uri="+redirect_uri+"&appid="+hospitalServices.getAppid()+"&response_type=code&scope=snsapi_base&state=1#wechat_redirect");
        response.sendRedirect(url.toString());//这里请不要使用get请求单纯的将页面跳转到该url即可
    }

    @RequestMapping("/toSuggestion1")
    public String toSuggestion1(HttpServletRequest request,Model model) {
        String code = request.getParameter("code");//微信活返回code值，用code获取openid
        String openid = WeixinUtil.getOpendId(code);
        List<Map> list = suggestionServices.wxgetSuggestion(openid);
        model.addAttribute("list",list);
        model.addAttribute("listSize",list.size());
        return view+"wxgetSuggestion";
    }


}
