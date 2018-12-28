package com.xinshai.xinshai.controller;

import com.xinshai.xinshai.entiry.menu.Button;
import com.xinshai.xinshai.entiry.menu.ClickButton;
import com.xinshai.xinshai.entiry.menu.Menu;
import com.xinshai.xinshai.entiry.menu.ViewButton;
import com.xinshai.xinshai.model.WeixinMenu;
import com.xinshai.xinshai.services.HospitalServices;
import com.xinshai.xinshai.services.WeixinMenuServices;
import com.xinshai.xinshai.servlet.TokenThread;
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

    @Resource
    private HospitalServices hospitalServices;

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


    /*ViewButton button11 = new ViewButton();
        button11.setName("中心简介");
        button11.setType("view");
        button11.setUrl("http://yc.jxcdc.cn/show.aspx?id=25&cid=20");

    ViewButton button12 = new ViewButton();
        button12.setName("筛查告知");
        button12.setType("view");
        button12.setUrl(DomainUrl.getUrl() + "/informing/informing");

    ViewButton button13 = new ViewButton();
        button13.setName("筛查项目");
        button13.setType("view");
        button13.setUrl(DomainUrl.getUrl() + "combine/toCombine");

    ViewButton button14 = new ViewButton();
        button14.setName("筛查流程");
        button14.setType("view");
        button14.setUrl("https://mp.weixin.qq.com/s/cFFTCkcRMr4wTKUPr2UzUw");

    ViewButton button15 = new ViewButton();
        button15.setName("联系我们");
        button15.setType("view");
        button15.setUrl("https://mp.weixin.qq.com/s/cFFTCkcRMr4wTKUPr2UzUw");

    List<Button> sub_button1 = new ArrayList<Button>();
        sub_button1.add(button11);
        sub_button1.add(button12);
        sub_button1.add(button13);
        sub_button1.add(button14);
        sub_button1.add(button15);

    Button button1 = new Button();
        button1.setName("筛查中心");
        button1.setSub_button(sub_button1);


    ViewButton button21 = new ViewButton();
        button21.setName("最新资讯");
        button21.setType("view");
        button21.setUrl("http://www.lznsn.com/");

    ViewButton button22 = new ViewButton();
        button22.setName("位置导航");
        button22.setType("view");
        button22.setUrl("http://map.baidu.com/mobile");

    ViewButton button23 = new ViewButton();
        button23.setName("注意事项");
        button23.setType("view");
        button23.setUrl("http://www.mama.cn/z/art/8736/");

    ClickButton button24 = new ClickButton();
        button24.setName("地理位置");
        button24.setType("location_select");
        button24.setKey("32");

    List<Button> sub_button2 = new ArrayList<Button>();
        sub_button2.add(button21);
        sub_button2.add(button22);
        sub_button2.add(button23);
        sub_button2.add(button24);

    Button button2 = new Button();
        button2.setName("服务咨询");
        button2.setSub_button(sub_button2);


    ViewButton button31 = new ViewButton();
        button31.setName("报告寄送");
        button31.setType("view");
        button31.setUrl(DomainUrl.getUrl() + "combine/listCombine");

    ViewButton button32 = new ViewButton();
        button32.setName("我的信息");
        button32.setType("view");
        button32.setUrl(DomainUrl.getUrl() + "personalData/personal");

    ViewButton button33 = new ViewButton();
        button33.setName("筛查报告");
        button33.setType("view");
        button33.setUrl(DomainUrl.getUrl() + "reportQuery/reportByOpenid");

    ClickButton button34 = new ClickButton();
        button34.setName("扫码事件");
        button34.setType("scancode_push");
        button34.setKey("31");

    List<Button> sub_button3 = new ArrayList<Button>();
        sub_button3.add(button31);
        sub_button3.add(button32);
        sub_button3.add(button33);
        sub_button3.add(button34);

    Button button = new Button();
        button.setName("筛查结果");
        button.setSub_button(sub_button3);

    List<Button> list=new ArrayList<Button>();
        list.add(button1);
        list.add(button2);
        list.add(button);

        menu.setButton(list);*/


    //更新微信公众号上面的菜单，
    @ResponseBody
    @RequestMapping("/updateWeChatMenu")
    public int updateWeChatMenu() {
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
                        if(1==w2.getMainName()){//不拼接域名
                            button.setUrl( w2.getUrl() );
                        }
                        if(0==w2.getMainName()){//拼接域名
                            button.setUrl( hospitalServices.getDomainUrl() + w2.getUrl());
                        }
                        sub_button1.add(button);
                    }
                    if("click".equals( w2.getType() ) ){
                        ClickButton button = new ClickButton();
                        button.setName(w2.getName());
                        button.setType(w2.getType());
                        button.setKey(w2.getMenukey());
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

        //此处要注意，如果再次调用WeixinUtil.getAccessToken()获取token,下次在通过TokenThread获取token时会报
        //40001，错误信息：invalid credential, access_token is invalid or not latest hint，
        // 这个错误是access_token是非法的或者不是最新的token，建议你刷新一下token或者检查一下你在token赋值的过程中是否有语法错误。
        // 所有获取token只能在一个地方定时去刷token,
        //现在的bug是每次更新公众号菜单时，获取token都失败，终于找到原因，是再次通过微信接口去调用了，导致线程里的token失效

        result = WeixinUtil.createMenu(TokenThread.accessToken.getToken(),menuStr);//创建菜单
        //result = WeixinUtil.createMenu(WeixinUtil.getAccessToken().getToken(),menuStr);//创建菜单

        if(result==0){
            System.out.println("创建菜单成功");
        }else{
            System.out.println("错误码"+result);
        }
        return result;
    }





    @ResponseBody
    @RequestMapping("/updateMenuState")
    public void updateMenuState(String id){
        weixinMenuServices.updateMenuState(id);
    }



}
