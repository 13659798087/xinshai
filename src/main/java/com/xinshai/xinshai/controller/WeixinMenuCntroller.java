package com.xinshai.xinshai.controller;

import com.xinshai.xinshai.model.WeixinMenu;
import com.xinshai.xinshai.services.WeixinMenuServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/open/weixinMenu")
public class WeixinMenuCntroller {

    @Resource
    private WeixinMenuServices weixinMenuServices;

    private String view  = "weixinMenu/";

    @RequestMapping("/weixinMenu")
    public String weixinMenu(){
        return view + "weixinMenu";
    }

    @RequestMapping("/getWeixinMenu")
    public String getWeixinMenu(){
        return view + "getWeixinMenu";
    }

    @ResponseBody
    @RequestMapping("/getData")
    public List<WeixinMenu> getData(){
        List<WeixinMenu> weixinMenu = weixinMenuServices.getWeixinMenu();
        return weixinMenu;
    }

    @RequestMapping("/addWeixinMenu")
    public String addWeixinMenu(Model model, String id,String name,String type,String url,
                                String orderNum, String menukey, String sign,String editType){

        model.addAttribute("id",id);
        model.addAttribute("name",name);
        model.addAttribute("url",url);
        model.addAttribute("orderNum",orderNum);
        model.addAttribute("menukey",menukey);
        model.addAttribute("type",type);

        model.addAttribute("editType",editType);
        model.addAttribute("sign",sign);//标识是修改还是编辑
        return view+"addWeixinMenu";
    }

}
