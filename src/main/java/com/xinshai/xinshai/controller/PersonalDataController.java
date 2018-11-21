package com.xinshai.xinshai.controller;

import com.xinshai.xinshai.model.PersonBinding;
import com.xinshai.xinshai.services.PatientsServices;
import com.xinshai.xinshai.services.PersonalDataServices;
import com.xinshai.xinshai.services.WeixinUserInfoServices;
import com.xinshai.xinshai.util.DomainUrl;
import com.xinshai.xinshai.util.Guid;
import com.xinshai.xinshai.util.SignUtil;
import com.xinshai.xinshai.util.WeixinUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/personalData")
public class PersonalDataController {

    private String view = "personalData/";

    @Resource
    private PersonalDataServices personalDataServices;

    @Resource
    private WeixinUserInfoServices weixinUserInfoServices;


    @RequestMapping("/personal")
    public void personal(HttpServletRequest request, HttpServletResponse response)throws IOException{
        //这里要将你的授权回调地址处理一下，否则微信识别不了
        String redirect_uri= URLEncoder.encode(DomainUrl.getUrl()+"personalData/haveEntered", "UTF-8");
        //简单获取openid的话参数response_type与scope与state参数固定写死即可
        StringBuffer url=new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize?redirect_uri="+redirect_uri+"&appid="+WeixinUtil.APPID+"&response_type=code&scope=snsapi_base&state=1#wechat_redirect");
        response.sendRedirect(url.toString());//这里请不要使用get请求单纯的将页面跳转到该url即可
    }

    @RequestMapping("/haveEntered")
    public String haveEntered(Model model,HttpServletRequest request, HttpServletResponse response)throws IOException{
        String code = request.getParameter("code");//微信活返回code值，用code获取openid
        String openId = WeixinUtil.getOpendId(code);

        List<PersonBinding> listWeixinMsg =  personalDataServices.getMsgByOpenid(openId);

        if(listWeixinMsg.size()>0){
            model.addAttribute("listWeixinMsg",listWeixinMsg);
            return  view+"continueAdd";
        }else{
            return view+"personal";
        }
    }

    @RequestMapping("/addInformation")
    public String addInformation(){
        return view+"addInformation";
    }


    //在后台数据库插入关注用户绑定的个人信息，用户绑定成功之后，接着就去验证该用户是否存在数据库
    @RequestMapping("/submitInformation")
    public void submitInformation(HttpServletRequest request, HttpServletResponse response,String m_name,
                                  String tel,String bithday,String bloodCard) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        //这里要将你的授权回调地址处理一下，否则微信识别不了
        String redirect_uri= URLEncoder.encode(DomainUrl.getUrl()+"personalData/continueAdd?m_name="+m_name+"&tel="
                +tel+"&bithday="+bithday+"&bloodCard="+bloodCard, "UTF-8");

        //简单获取openid的话参数response_type与scope与state参数固定写死即可
        StringBuffer url=new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize?redirect_uri="+redirect_uri+"&appid="+WeixinUtil.APPID+"&response_type=code&scope=snsapi_base&state=1#wechat_redirect");
        response.sendRedirect(url.toString());//这里请不要使用get请求单纯的将页面跳转到该url即可

    }


    @RequestMapping("/continueAdd")
    public String togo(Model model,HttpServletRequest request, HttpServletResponse response, String m_name,
                       String tel, String bithday, String bloodCard) {

        String code = request.getParameter("code");//微信活返回code值，用code获取openid

        String openId = WeixinUtil.getOpendId(code);

        personalDataServices.createWeixinMsg(Guid.GenerateGUID(),openId,m_name,bloodCard,tel,bithday);
        //绑定成功之后，将该微信用户的标识update,0代表已绑定，1代表未绑定
        weixinUserInfoServices.bindMessage(openId);

        List<PersonBinding> listWeixinMsg =  personalDataServices.getMsgByOpenid(openId);

        //System.out.println("-------code-------"+code+"\n"+"-------openId-------"+openId);

        model.addAttribute("listWeixinMsg",listWeixinMsg);
        return  view+"continueAdd";
    }

    @ResponseBody
    @RequestMapping("/deleteBind")
    public String deleteBind(String id){
        personalDataServices.deleteBind(id);
        return "redirect:personal";
    }

    @RequestMapping("/sdkTest")
    public String sdkTest(){
        return view + "sdkTest";
    }

    @ResponseBody
    @RequestMapping("/get1")
    public Map get1(String url){

        Map map = new HashMap();

        map = SignUtil.getSign(url);

        return map;
    }


}
