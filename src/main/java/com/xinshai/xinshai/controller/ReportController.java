package com.xinshai.xinshai.controller;

import com.xinshai.xinshai.entiry.Report;
import com.xinshai.xinshai.model.Combine;
import com.xinshai.xinshai.model.PersonBinding;
import com.xinshai.xinshai.model.ResultPushMsg;
import com.xinshai.xinshai.services.CombineServices;
import com.xinshai.xinshai.services.PersonalDataServices;
import com.xinshai.xinshai.util.DomainUrl;
import com.xinshai.xinshai.util.Guid;
import com.xinshai.xinshai.util.JasperUtil;
import com.xinshai.xinshai.util.WeixinUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("/reportQuery")
public class ReportController {

    @Resource
    private CombineServices combineServices;

    @Resource
    private PersonalDataServices personalDataServices;

    @Resource
    private JasperUtil jasperUtil;

    @Value("${filePath}")
    private String filePath;

    private String view = "reportQuery/";

    @RequestMapping("/pcAndPhone")
    public String pcAndPhone(){
        return view + "pcAndPhone";
    }

    @RequestMapping("/report")
    public String report(){
        return view + "report";
    }

    @RequestMapping("/phoneReport")
    public String phoneReport(){
        return view + "phoneReport";
    }

    @RequestMapping("/reportManage")
    public String reportManage(){
        return view + "reportManage";
    }

    @RequestMapping("/reportPreview")
    public String reportPreview(String c_id, String authorHospital, String c_code, Model model){
        model.addAttribute("c_id",c_id);
        model.addAttribute("authorHospital",authorHospital);
        model.addAttribute("c_code",c_code);

        return view + "reportPreview";
    }

    @RequestMapping("/printReport")
    public void myprint(HttpServletResponse response,String c_id,String c_code,String authorHospital) throws Exception {
        String fileName = "";

        List<Combine> combine = combineServices.getCombine();
        for(Combine c : combine){
            if(c_code.equals(c.getC_code())){
                fileName = c.getC_rpt()+".jasper";//拼接文件后缀pdf,此处是下载pdf文件
            }
        }

        InputStream in = genPdf(c_id,fileName,authorHospital);
        response.setContentType("application/pdf; charset=UTF-8");
        OutputStream out = response.getOutputStream();
        byte[] b = new byte[512];
            if( in!=null ){
            while ((in.read(b))!=-1) {
                out.write(b);
            }
        }
        out.flush();
        in.close();
        out.close();
    }


    public InputStream genPdf(String c_id,String fileName,String authorHospital) {
        List<Report> userLs = new ArrayList<Report>();
        Map<String, Object> map = new HashMap<String, Object>();
        /*map.put("cCombine", "ct"); map.put("cSid", "C1700530");*//*map.put("c_combine", "cl"); map.put("c_sid", "20170967");*///map.put("c_id", "ts180529001");//map.put("c_id", " wc20180529002");// map.put("c_id", "dp20180529001");
        map.put("c_id", c_id);
        map.put("authorHospital", authorHospital);
        return jasperUtil.exportPdfDir(fileName, map, userLs);
    }


    //在后台数据库插入关注用户绑定的个人信息，用户绑定成功之后，接着就去验证该用户是否存在数据库
    @RequestMapping("/reportByOpenid")
    public void submitInformation(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        //这里要将你的授权回调地址处理一下，否则微信识别不了
        String redirect_uri= URLEncoder.encode(DomainUrl.getUrl()+"reportQuery/getResultItem", "UTF-8");

        //简单获取openid的话参数response_type与scope与state参数固定写死即可
        StringBuffer url=new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize?redirect_uri="+redirect_uri+"&appid="+WeixinUtil.APPID+"&response_type=code&scope=snsapi_base&state=1#wechat_redirect");
        response.sendRedirect(url.toString());//这里请不要使用get请求单纯的将页面跳转到该url即可

    }


    @RequestMapping("/getResultItem")
    public String getResultItem(Model model,HttpServletRequest request) {

        String code = request.getParameter("code");//微信活返回code值，用code获取openid

        String openId = WeixinUtil.getOpendId(code);

        List<ResultPushMsg> list = null;

        list = personalDataServices.getResultItem(openId);

        //System.out.println("-------code-------"+code+"\n"+"-------openId-------"+openId);

        model.addAttribute("list",list);
        model.addAttribute("listSize",list.size());
        return  view+"getResultItem";
    }

}
