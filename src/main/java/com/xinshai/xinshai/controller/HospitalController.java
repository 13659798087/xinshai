package com.xinshai.xinshai.controller;

import com.xinshai.xinshai.entiry.PageResults;
import com.xinshai.xinshai.model.Hospital;
import com.xinshai.xinshai.services.HospitalServices;
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
@RequestMapping("/hospital")
public class HospitalController {

    private String  view ="hospital/";
    @Resource
    private HospitalServices hospitalServices;
    //natapp生成的域名
    //return "localhost:8070/";
    //return "http://p5fvsu.natappfree.cc/";
    //return "http://wx.lznsn.com/";
    /**
     * 获取微信appid,appsecret,域名等信息
     */
    public String getDomainUrl(){
        return hospitalServices.getDomainUrl();
    }

    public String getAppid(){
        return hospitalServices.getAppid();
    }

    public String getAppsecret(){
        return hospitalServices.getAppsecret();
    }





    @RequestMapping("/tengxunMap")
    public String tengxunMap(Model model){
        Hospital hospital = null;
        hospital = hospitalServices.getLocationById();
        model.addAttribute("hospital",hospital);
        return view + "tengxunMap";
    }

    @RequestMapping("/hospital")
    public String informing(String sign,Model model){
        model.addAttribute("sign",sign);
        return view + "hospital";
    }

    @ResponseBody
    @RequestMapping("/getHospital")
    public Map<String,Object> getLocation(String pageNumber,String rowNumber,String sortName,String sortOrder){

        List<Hospital> location = new ArrayList<Hospital>();

        int pageNo = Integer.parseInt((pageNumber == null || pageNumber =="0") ? "1":pageNumber);
        int pageSize = Integer.parseInt((rowNumber == null || rowNumber =="0") ? "10":rowNumber);
        String orderBy = sortName == null  ? "updaterTime":sortName;
        String order = sortOrder == null  ? PageResults.DESC:sortOrder;

        PageResults<Hospital> pageResults = new PageResults<Hospital>();
        pageResults.setPageNo(pageNo);
        pageResults.setPageSize(pageSize);
        pageResults.setOrderBy(orderBy);
        pageResults.setOrder(order);

        location = hospitalServices.getLocation();

        long totalCount = hospitalServices.getLocationCount();

        pageResults.setResult(location);
        pageResults.setTotalCount(totalCount);
        return Paging.ajaxGrid(pageResults);
    }

    /**
     创建地理位置
     */
    @RequestMapping("/editHospital")
    public String createLocation(String h_name,String h_address_name,String h_address,String h_latitude,String h_longitude,
                                 String h_scale,String h_infoUrl,String weixinName,String appid,String appsecret){
        hospitalServices.updateLocation(h_name,h_address_name,h_address,h_latitude,h_longitude,h_scale,h_infoUrl,weixinName,appid,appsecret);
        return "redirect:hospital?sign=edit"; //重定向
    }

    @RequestMapping("/addHospital")
    public String addLocation(Model model,String h_name,String h_address_name,String h_address,String h_latitude,
                      String h_longitude,String h_scale,String h_infoUrl,String weixinName,String appid,String appsecret){

        model.addAttribute("h_name",h_name);
        model.addAttribute("h_address_name",h_address_name);
        model.addAttribute("h_address",h_address);
        model.addAttribute("h_latitude",h_latitude);
        model.addAttribute("h_longitude",h_longitude);
        model.addAttribute("h_scale",h_scale);
        model.addAttribute("h_infoUrl",h_infoUrl);

        model.addAttribute("weixinName",weixinName);
        model.addAttribute("appid",appid);
        model.addAttribute("appsecret",appsecret);

        return view+"addHospital";
    }

    @ResponseBody
    @RequestMapping("/deleteById")
    public void deleteById(String id){
        hospitalServices.deleteById(id);
    }

    @ResponseBody
    @RequestMapping("/getLocationById")
    public Hospital getLocationById(){
        Hospital location = null;
        location = hospitalServices.getLocationById();
        return location;
    }

    @RequestMapping("/map")
    public String map(){
        return view+"map";
    }


}
