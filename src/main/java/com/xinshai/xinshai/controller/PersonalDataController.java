package com.xinshai.xinshai.controller;

import com.xinshai.xinshai.model.WeixinMsg;
import com.xinshai.xinshai.services.PersonalDataServices;
import com.xinshai.xinshai.util.DomainUrl;
import com.xinshai.xinshai.util.Guid;
import com.xinshai.xinshai.util.WeixinUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/personalData")
public class PersonalDataController {

    private String view = "personalData/";

    private String appID = "wx362203c9bf705039";

    private String appsecret = "d03fb25dda90c511d6becde4bf9a3cbf";


    @Resource
    private PersonalDataServices personalDataServices;

    @RequestMapping("/personal")
    public void personal(HttpServletRequest request, HttpServletResponse response)throws IOException{
        //这里要将你的授权回调地址处理一下，否则微信识别不了
        String redirect_uri= URLEncoder.encode(DomainUrl.getUrl()+"/personalData/haveEntered", "UTF-8");
        //简单获取openid的话参数response_type与scope与state参数固定写死即可
        StringBuffer url=new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize?redirect_uri="+redirect_uri+"&appid="+appID+"&response_type=code&scope=snsapi_base&state=1#wechat_redirect");
        response.sendRedirect(url.toString());//这里请不要使用get请求单纯的将页面跳转到该url即可

       // return view+"personal";
    }

    @RequestMapping("/haveEntered")
    public String haveEntered(Model model,HttpServletRequest request, HttpServletResponse response)throws IOException{
        String code = request.getParameter("code");//微信活返回code值，用code获取openid
        String openId = WeixinUtil.getOpendId(code);

        List<WeixinMsg> listWeixinMsg =  personalDataServices.getMsgByOpenid(openId);

        if(listWeixinMsg.size()>0){
            model.addAttribute("listWeixinMsg",listWeixinMsg);
            return  view+"continueAdd";
        }else{
            return view+"personal";
        }

    }

    @RequestMapping("/queryReport")
    public String queryReport(){
        return view+"queryReport";
    }

    @RequestMapping("/addInformation")
    public String addInformation(){
        return view+"addInformation";
    }


    @RequestMapping("/submitInformation")
    public void submitInformation(HttpServletRequest request, HttpServletResponse response,String p_name,String p_tel,String s_birthday) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        //这里要将你的授权回调地址处理一下，否则微信识别不了
        String redirect_uri= URLEncoder.encode(DomainUrl.getUrl()+"/personalData/continueAdd?p_name="+p_name+"&p_tel="+p_tel+"&s_birthday="+s_birthday, "UTF-8");
        //简单获取openid的话参数response_type与scope与state参数固定写死即可
        StringBuffer url=new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize?redirect_uri="+redirect_uri+"&appid="+appID+"&response_type=code&scope=snsapi_base&state=1#wechat_redirect");
        response.sendRedirect(url.toString());//这里请不要使用get请求单纯的将页面跳转到该url即可

    }


    @RequestMapping("/continueAdd")
    public String togo(Model model,HttpServletRequest request, HttpServletResponse response, String p_name, String p_tel, String s_birthday) throws IOException {
        String code = request.getParameter("code");//微信活返回code值，用code获取openid

        String openId = WeixinUtil.getOpendId(code);

        Timestamp date_1 = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String a = format.format(new Date()).substring(10,19);//也截取空格
        s_birthday = s_birthday + a;
        date_1 = Timestamp.valueOf(s_birthday);

        personalDataServices.createWeixinMsg(Guid.GenerateGUID(),openId,p_name,p_tel,date_1);
        List<WeixinMsg> listWeixinMsg =  personalDataServices.getMsgByOpenid(openId);

        //System.out.println("-------code-------"+code+"\n"+"-------openId-------"+openId);

        model.addAttribute("listWeixinMsg",listWeixinMsg);
        return  view+"continueAdd";
    }

    @RequestMapping("/deleteBind")
    public String deleteBind(String id){
        personalDataServices.deleteBind(id);
        return "redirect:personal";
    }


}
