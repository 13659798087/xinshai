package com.xinshai.xinshai.controller;

import com.xinshai.xinshai.entiry.menu.Button;
import com.xinshai.xinshai.entiry.menu.ClickButton;
import com.xinshai.xinshai.entiry.menu.Menu;
import com.xinshai.xinshai.entiry.menu.ViewButton;
import com.xinshai.xinshai.model.WeixinMenu;
import com.xinshai.xinshai.services.WeixinMenuServices;
import com.xinshai.xinshai.util.DomainUrl;
import com.xinshai.xinshai.util.Guid;
import com.xinshai.xinshai.util.WeixinUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
    public String addWeixinMenu(Model model, String id,String name,String type,String url,String pid,
                                String orderNum, String menukey, String sign,String editType){

        model.addAttribute("id",id);
        model.addAttribute("name",name);
        model.addAttribute("url",url);
        model.addAttribute("orderNum",orderNum);
        model.addAttribute("menukey",menukey);
        model.addAttribute("type",type);
        model.addAttribute("pid",pid);

        model.addAttribute("editType",editType);
        model.addAttribute("sign",sign);//标识是修改还是编辑
        return view+"addWeixinMenu";
    }


    @RequestMapping("/changeLocalMenu")
    public String changeLocalMenu(HttpServletRequest request,String id,String name,String pid,
                                  String type,String url,String menukey,String orderNum){
        String sign = "";

        //修改
        if(!StringUtils.isEmpty(id)){
            weixinMenuServices.updateLocalMenu(id,name,type,url,menukey,orderNum,pid);
            sign = "edit";
        }else{
            //新增
            id = Guid.GenerateGUID();
            weixinMenuServices.creatMenu(id,name,type,url,menukey,orderNum,pid);
            sign = "add";
        }

        return "redirect:addWeixinMenu?type=a&sign="+sign; //重定向
    }


    @ResponseBody
    @RequestMapping("/parentList")
    public List<WeixinMenu> parentList(){
        List<WeixinMenu> weixinMenu = weixinMenuServices.parentList();
        return weixinMenu;
    }

    //更新微信公众号上面的菜单，
    @ResponseBody
    @RequestMapping("/updateWeChatMenu")
    public String uupdateWeChatMenu() {

        Menu menu = new Menu();

        List<WeixinMenu> weixin = weixinMenuServices.getWeixinMenu();

        //一级菜单
        List<WeixinMenu> weixin1 = new ArrayList<WeixinMenu>();

        for(WeixinMenu w : weixin ){
            if( StringUtils.isEmpty(w.getPid()) ){
                weixin1.add(w); //.....一级菜单
            }
        }

        List<Button> list=new ArrayList<Button>();
        for(WeixinMenu w : weixin1 ){//一级菜单
            Button button1 = new Button();
            List<Button> sub_button1 = new ArrayList<Button>();
            for(WeixinMenu w2 : weixin ){
                if( w.getId().equals(w2.getPid()) ){ //如果pid等于id，说明该记录w2是二级菜单

                    if("view".equals( w2.getType() ) ){
                        ViewButton button = new ViewButton();
                        button.setName(w2.getName());
                        button.setType(w2.getType());
                        button.setUrl(DomainUrl.getUrl() + w2.getUrl());
                        sub_button1.add(button);
                    }
                    //scancode_push扫码事件，location_select弹出地理位置
                    if("scancode_push".equals( w2.getType() ) || "location_select".equals(w2.getType()) ){
                        ClickButton button = new ClickButton();
                        button.setName(w2.getName());
                        button.setType(w2.getType());
                        button.setKey(w2.getMenukey());
                        sub_button1.add(button);
                    }

                }
            }
            button1.setName(w.getName());
            button1.setSub_button(sub_button1);

            list.add(button1);
        }

        menu.setButton(list);

        String menuStr  = JSONObject.fromObject(menu).toString();
        int result = 0;

        result = WeixinUtil.createMenu(WeixinUtil.getAccessToken().getToken(),menuStr);//创建菜单

        if(result==0){
            System.out.println("创建菜单成功");
        }else{
            System.out.println("错误码"+result);
        }

        return "创建菜单成功";
    }


    @ResponseBody
    @RequestMapping("/updateMenuState")
    public void updateMenuState(String id){
        weixinMenuServices.updateMenuState(id);
    }

}
