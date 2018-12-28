package com.xinshai.xinshai.controller;

import com.xinshai.xinshai.entiry.ReportMsg;
import com.xinshai.xinshai.model.PersonBinding;
import com.xinshai.xinshai.model.Signpic;
import com.xinshai.xinshai.services.*;
import com.xinshai.xinshai.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
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

    @Resource
    private OperationLogServices operationLogServices;

    @Resource
    private HospitalServices hospitalServices;

    @Resource
    private ConfigServices configServices;

    @Value("${jumpPage}")
    private String jumpPage;


    @RequestMapping("/personal")
    public void personal(HttpServletRequest request, HttpServletResponse response)throws IOException{
        //这里要将你的授权回调地址处理一下，否则微信识别不了

        String redirect_uri= URLEncoder.encode(hospitalServices.getDomainUrl() + "personalData/haveEntered", "UTF-8");
        //String redirect_uri= URLEncoder.encode("localhost:8070/"+"personalData/haveEntered", "UTF-8");

        //简单获取openid的话参数response_type与scope与state参数固定写死即可
        StringBuffer url=new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize?redirect_uri="+redirect_uri+"&appid="+hospitalServices.getAppid()+"&response_type=code&scope=snsapi_base&state=1#wechat_redirect");
        response.sendRedirect(url.toString());//这里请不要使用get请求单纯的将页面跳转到该url即可

        //日志，查看绑定信息推送
    }

    @RequestMapping("/haveEntered")
    public String haveEntered(Model model,HttpServletRequest request, HttpServletResponse response) throws Exception {
        String code = request.getParameter("code");//微信活返回code值，用code获取openid
        String openId = WeixinUtil.getOpendId(code);

        List<PersonBinding> listWeixinMsg =  personalDataServices.getMsgByOpenid(openId);
        //电话解密
        for(PersonBinding  li : listWeixinMsg){
            if(li.getTel() != null){
                li.setTel(DESUtil.decryptor(li.getTel()));
            }
            if(li.getTel_2() != null){
                li.setTel_2(DESUtil.decryptor(li.getTel_2()));
            }
        }


        String pageParam = configServices.getConfigByCode(jumpPage);
        model.addAttribute("pageParam",pageParam);

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

    @RequestMapping("/xinShaiAdd")
    public String scanAdd(){
        return view+"xinShaiAdd";
    }


    //在后台数据库插入关注用户绑定的个人信息，用户绑定成功之后，接着就去验证该用户是否存在数据库
    @RequestMapping("/submitInformation")
    public void submitInformation(HttpServletRequest request,HttpServletResponse response,String m_name,String m_age,
                       String tes,String pregnanciesNum,String hospitalNo,String tel_2,String address,String tel,
                       String deliverNum,String f_name, String deviceName,String ipAddress,
                       String bloodCard,String pregnancyWeek,String pregnancyDay,String bithday,String sex,String weigh,
                       String bloodCard2,String pregnancyWeek2,String pregnancyDay2,String bithday2,String sex2,String weigh2,
                       String bloodCard3,String pregnancyWeek3,String pregnancyDay3,String bithday3,String sex3,String weigh3,
                       String bloodCard4,String pregnancyWeek4,String pregnancyDay4,String bithday4,String sex4,String weigh4
                       ) throws IOException {


        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        //这里要将你的授权回调地址处理一下，否则微信识别不了'

        String params = "";
        if(!StringUtils.isEmpty(tes)) params += "&tes="+tes;
        if(!StringUtils.isEmpty(hospitalNo)) params += "&hospitalNo="+hospitalNo;
        if(!StringUtils.isEmpty(m_age)) params += "&m_age="+m_age;
        if(!StringUtils.isEmpty(f_name)) params += "&f_name="+f_name;
        if(!StringUtils.isEmpty(tel_2)) params += "&tel_2="+tel_2;
        if(!StringUtils.isEmpty(address)) params += "&address="+address;
        if(!StringUtils.isEmpty(pregnanciesNum)) params += "&pregnanciesNum="+pregnanciesNum;
        if(!StringUtils.isEmpty(deliverNum)) params += "&deliverNum="+deliverNum;
        if(!StringUtils.isEmpty(pregnancyWeek)) params += "&pregnancyWeek="+pregnancyWeek;
        if(!StringUtils.isEmpty(pregnancyDay)) params += "&pregnancyDay="+pregnancyDay;
        if(!StringUtils.isEmpty(sex)) params += "&sex="+sex;
        if(!StringUtils.isEmpty(weigh)) params += "&weigh="+weigh;

        if("2".equals(tes)){
            if(!StringUtils.isEmpty(bloodCard2)) params += "&bloodCard2="+bloodCard2;
            if(!StringUtils.isEmpty(pregnancyWeek2)) params += "&pregnancyWeek2="+pregnancyWeek2;
            if(!StringUtils.isEmpty(pregnancyDay2)) params += "&pregnancyDay2="+pregnancyDay2;
            if(!StringUtils.isEmpty(bithday2)) params += "&bithday2="+bithday2;
            if(!StringUtils.isEmpty(sex2)) params += "&sex2="+sex2;
            if(!StringUtils.isEmpty(weigh2)) params += "&weigh2="+weigh2;
        }

        if("3".equals(tes)){
            if(!StringUtils.isEmpty(bloodCard2)) params += "&bloodCard2="+bloodCard2;
            if(!StringUtils.isEmpty(pregnancyWeek2)) params += "&pregnancyWeek2="+pregnancyWeek2;
            if(!StringUtils.isEmpty(pregnancyDay2)) params += "&pregnancyDay2="+pregnancyDay2;
            if(!StringUtils.isEmpty(bithday2)) params += "&bithday2="+bithday2;
            if(!StringUtils.isEmpty(sex2)) params += "&sex2="+sex2;
            if(!StringUtils.isEmpty(weigh2)) params += "&weigh2="+weigh2;

            if(!StringUtils.isEmpty(bloodCard3)) params += "&bloodCard3="+bloodCard3;
            if(!StringUtils.isEmpty(pregnancyWeek3)) params += "&pregnancyWeek3="+pregnancyWeek3;
            if(!StringUtils.isEmpty(pregnancyDay3)) params += "&pregnancyDay3="+pregnancyDay3;
            if(!StringUtils.isEmpty(bithday3)) params += "&bithday3="+bithday3;
            if(!StringUtils.isEmpty(sex3)) params += "&sex3="+sex3;
            if(!StringUtils.isEmpty(weigh3)) params += "&weigh3="+weigh3;
        }

        if("4".equals(tes)){
            if(!StringUtils.isEmpty(bloodCard2)) params += "&bloodCard2="+bloodCard2;
            if(!StringUtils.isEmpty(pregnancyWeek2)) params += "&pregnancyWeek2="+pregnancyWeek2;
            if(!StringUtils.isEmpty(pregnancyDay2)) params += "&pregnancyDay2="+pregnancyDay2;
            if(!StringUtils.isEmpty(bithday2)) params += "&bithday2="+bithday2;
            if(!StringUtils.isEmpty(sex2)) params += "&sex2="+sex2;
            if(!StringUtils.isEmpty(weigh2)) params += "&weigh2="+weigh2;

            if(!StringUtils.isEmpty(bloodCard3)) params += "&bloodCard3="+bloodCard3;
            if(!StringUtils.isEmpty(pregnancyWeek3)) params += "&pregnancyWeek3="+pregnancyWeek3;
            if(!StringUtils.isEmpty(pregnancyDay3)) params += "&pregnancyDay3="+pregnancyDay3;
            if(!StringUtils.isEmpty(bithday3)) params += "&bithday3="+bithday3;
            if(!StringUtils.isEmpty(sex3)) params += "&sex3="+sex3;
            if(!StringUtils.isEmpty(weigh3)) params += "&weigh3="+weigh3;

            if(!StringUtils.isEmpty(bloodCard4)) params += "&bloodCard4="+bloodCard4;
            if(!StringUtils.isEmpty(pregnancyWeek4)) params += "&pregnancyWeek4="+pregnancyWeek4;
            if(!StringUtils.isEmpty(pregnancyDay4)) params += "&pregnancyDay4="+pregnancyDay4;
            if(!StringUtils.isEmpty(bithday4)) params += "&bithday4="+bithday4;
            if(!StringUtils.isEmpty(sex4)) params += "&sex4="+sex4;
            if(!StringUtils.isEmpty(weigh4)) params += "&weigh4="+weigh4;
        }

        if(!StringUtils.isEmpty(deviceName)) params += "&deviceName="+deviceName;
        if(!StringUtils.isEmpty(ipAddress)) params += "&ipAddress="+ipAddress;

        //String redirect_uri= URLEncoder.encode("localhost:8070/"+"personalData/continueAdd?m_name="+m_name+"&tel="+tel
        String redirect_uri= URLEncoder.encode(hospitalServices.getDomainUrl() + "personalData/continueAdd?m_name="+m_name+"&tel="+tel
              +"&bithday="+bithday+"&bloodCard="+bloodCard+params, "UTF-8");

        //简单获取openid的话参数response_type与scope与state参数固定写死即可
        StringBuffer url=new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize?redirect_uri="+redirect_uri+"&appid="+hospitalServices.getAppid()+"&response_type=code&scope=snsapi_base&state=1#wechat_redirect");
        response.sendRedirect(url.toString());//这里请不要使用get请求单纯的将页面跳转到该url即可

    }


    @RequestMapping("/continueAdd")
    public String togo(Model model,HttpServletRequest request,String m_name,String m_age,String tel,String tes,String hospitalNo,String
                       f_name,String tel_2,String address, String pregnanciesNum,String deliverNum,String deviceName,String ipAddress,
                       String bloodCard,String pregnancyWeek,String pregnancyDay,String bithday,String sex,String weigh,
                       String bloodCard2,String pregnancyWeek2,String pregnancyDay2,String bithday2,String sex2,String weigh2,
                       String bloodCard3,String pregnancyWeek3,String pregnancyDay3,String bithday3,String sex3,String weigh3,
                       String bloodCard4,String pregnancyWeek4,String pregnancyDay4,String bithday4,String sex4,String weigh4) throws Exception {

        String code = request.getParameter("code");//微信活返回code值，用code获取openid
        String openId = WeixinUtil.getOpendId(code);

        //personalDataServices.createWeixinMsg(Guid.GenerateGUID(),openId,m_name,bloodCard,tel,bithday);

        List<PersonBinding> list = new ArrayList<PersonBinding>();

        //电话加密
        if(!StringUtils.isEmpty(tel)){
            tel = DESUtil.encrypt(tel);
        }
        if(!StringUtils.isEmpty(tel_2)){
            tel_2 = DESUtil.encrypt(tel_2);
        }

        PersonBinding p1 = new PersonBinding();
        p1.setOpenId(openId);
        p1.setM_name(m_name);
        p1.setTel(tel);
        p1.setM_age(m_age);
        p1.setHospitalNo(hospitalNo);
        p1.setF_name(f_name);
        p1.setTel_2(tel_2);

        if( !StringUtils.isEmpty(tes)){
            p1.setTes( Integer.parseInt(tes) );
        }
        p1.setAddress(address);
        p1.setPregnanciesNum(pregnanciesNum);
        p1.setDeliverNum(deliverNum);
        /**以上是随胎儿数变，不变的数据*/

        p1.setId(Guid.GenerateGUID());
        p1.setBloodCard(bloodCard);
        p1.setPregnancyWeek(pregnancyWeek);
        p1.setPregnancyDay(pregnancyDay);
        p1.setBithday(bithday);

        if( !StringUtils.isEmpty(sex)){
            p1.setSex( Integer.parseInt(sex) );
        }
        p1.setWeigh(weigh);
        list.add(p1);

        /* personalDataServices.createpersonlMsg(Guid.GenerateGUID(),openId,m_name,bloodCard,tel,bithday,tes,hospitalNo,m_age,f_name,tel_2,address,pregnanciesNum,deliverNum,pregnancyWeek,pregnancyDay,sex,weigh);*/
         /**以下是随胎儿数变，可能会变的数据*/

        if("2".equals(tes)){
            PersonBinding p2 = new PersonBinding();
            //BeanUtils.copyProperties(源对象, 目标对象);
            BeanUtils.copyProperties(p1, p2);

            p2.setId(Guid.GenerateGUID());
            p2.setBloodCard(bloodCard2);
            p2.setPregnancyWeek(pregnancyWeek2);
            p2.setPregnancyDay(pregnancyDay2);
            p2.setBithday(bithday2);
            p2.setSex( Integer.parseInt(sex2) );
            p2.setWeigh(weigh2);
            list.add(p2);
        }
        if("3".equals(tes)){
            PersonBinding p2 = new PersonBinding();
            PersonBinding p3 = new PersonBinding();
            BeanUtils.copyProperties(p1, p2);
            BeanUtils.copyProperties(p1, p3);

            p2.setId(Guid.GenerateGUID());
            p2.setBloodCard(bloodCard2);
            p2.setPregnancyWeek(pregnancyWeek2);
            p2.setPregnancyDay(pregnancyDay2);
            p2.setBithday(bithday2);
            p2.setSex( Integer.parseInt(sex2) );
            p2.setWeigh(weigh2);
            list.add(p2);

            p3.setId(Guid.GenerateGUID());
            p3.setBloodCard(bloodCard3);
            p3.setPregnancyWeek(pregnancyWeek3);
            p3.setPregnancyDay(pregnancyDay3);
            p3.setBithday(bithday3);
            p3.setSex( Integer.parseInt(sex3) );
            p3.setWeigh(weigh3);
            list.add(p3);
        }
        if("4".equals(tes)){
            PersonBinding p2 = new PersonBinding();
            PersonBinding p3 = new PersonBinding();
            PersonBinding p4 = new PersonBinding();
            BeanUtils.copyProperties(p1, p2);
            BeanUtils.copyProperties(p1, p3);
            BeanUtils.copyProperties(p1, p4);

            p2.setId(Guid.GenerateGUID());
            p2.setBloodCard(bloodCard2);
            p2.setPregnancyWeek(pregnancyWeek2);
            p2.setPregnancyDay(pregnancyDay2);
            p2.setBithday(bithday2);
            p2.setSex( Integer.parseInt(sex2) );
            p2.setWeigh(weigh2);
            list.add(p2);

            p3.setId(Guid.GenerateGUID());
            p3.setBloodCard(bloodCard3);
            p3.setPregnancyWeek(pregnancyWeek3);
            p3.setPregnancyDay(pregnancyDay3);
            p3.setBithday(bithday3);
            p3.setSex( Integer.parseInt(sex3) );
            p3.setWeigh(weigh3);
            list.add(p3);

            p4.setId(Guid.GenerateGUID());
            p4.setBloodCard(bloodCard4);
            p4.setPregnancyWeek(pregnancyWeek4);
            p4.setPregnancyDay(pregnancyDay4);
            p4.setBithday(bithday4);
            p4.setSex( Integer.parseInt(sex4) );
            p4.setWeigh(weigh4);
            list.add(p4);
        }

        personalDataServices.insertBatchPersonMsg(list);

        //绑定成功之后，将该微信用户的标识update,0代表已绑定，1代表未绑定
        weixinUserInfoServices.bindMessage(openId);

        List<PersonBinding> listWeixinMsg =  personalDataServices.getMsgByOpenid(openId);
        for(PersonBinding  li : listWeixinMsg){ //电话解密
            if(li.getTel() != null){
                li.setTel(DESUtil.decryptor(li.getTel()));
            }
            if(li.getTel_2() != null){
                li.setTel_2(DESUtil.decryptor(li.getTel_2()));
            }
        }

        Signpic signpic = personalDataServices.get1();

        model.addAttribute("listWeixinMsg",listWeixinMsg);
        model.addAttribute("signpic",signpic);

        //日志--用户成功绑定个人信息的时间-----新增
        operationLogServices.recordLog(Guid.GenerateGUID(),"新增","成功绑定个人信息",openId,deviceName,ipAddress);

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
        map.put("APPID",hospitalServices.getAppid());
        return map;
    }


    //生成查询处的验证码
    @RequestMapping("/queryValidateCode")
    public void queryValidateCode(org.apache.catalina.servlet4preview.http.HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 通知浏览器不要缓存
        response.setHeader("Expires", "-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "-1");
        CaptchaUtil util = CaptchaUtil.Instance();
        // 将验证码输入到session中，用来验证
        String validateCode = util.getString();
        request.getSession().setAttribute("validateCode", validateCode);
        // 输出打web页面
        ImageIO.write(util.getImage(), "jpg", response.getOutputStream());
    }

    @RequestMapping("/personBarcode")
    public String personBarcode(Model model,String bloodCard,String m_name){
        model.addAttribute("bloodCard",bloodCard);
        model.addAttribute("m_name",m_name);
        return view + "personBarcode";
    }


}
