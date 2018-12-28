package com.xinshai.xinshai.controller;

import com.xinshai.xinshai.entiry.PageResults;
import com.xinshai.xinshai.model.Menu;
import com.xinshai.xinshai.model.WeixinMenu;
import com.xinshai.xinshai.services.MenuServices;
import com.xinshai.xinshai.util.Guid;
import com.xinshai.xinshai.util.Paging;
import com.xinshai.xinshai.util.SSO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private MenuServices menuServices;

    private String  view ="menu/";

    @RequestMapping("/listMenu")
    public String listMenu(){
        //查出所有菜单，还有此用户是什么角色
        List<Menu> menu = menuServices.getAllMenu();
        return view + "listMenu";
    }

    @RequestMapping("/menuTree")
    public String menuTree(){
        return view + "menuTree";
    }

    @ResponseBody
    @RequestMapping("/parentList")
    public List<Menu> parentList(){
        List<Menu> menu = menuServices.parentList();
        return menu;
    }

    @RequestMapping("/addMenu")
    public String addMenu(Model model, String id, String name, String type, String url, String pid,
                                String orderNum, String icons, String sign, String editType){

        model.addAttribute("id",id);
        model.addAttribute("name",name);
        model.addAttribute("url",url);
        model.addAttribute("orderNum",orderNum);
        model.addAttribute("icons",icons);
        model.addAttribute("type",type);
        model.addAttribute("pid",pid);

        model.addAttribute("editType",editType);
        model.addAttribute("sign",sign);//标识是修改还是编辑
        return view+"addMenu";
    }

    @ResponseBody
    @RequestMapping("/menuDisplay")
    public List<Menu> menuDisplay(){
        //查出所有菜单，还有此用户是什么角色
        List<Menu> menu = menuServices.menuDisplay();
        return menu;
    }


    @ResponseBody
    @RequestMapping("/getMenuTree")
    public List<Menu> getMenuTree(){
        //查出所以角色，还有此用户是什么角色
        List<Menu> menu = menuServices.getAllMenu();
        return menu;
    }


    @ResponseBody
    @RequestMapping("/getAllMenu")
    public List<Menu> getAllRole(){
        //查出所以角色，还有此用户是什么角色
        List<Menu> menu = menuServices.getAllMenu();
        return menu;
    }

    @ResponseBody
    @RequestMapping("/getTableMenu")
    public Map<String,Object> getUserRole(String pageNumber, String rowNumber, String sortName,
                                          String sortOrder, String menuName, HttpServletRequest request){

        List<Menu> menu = new ArrayList<Menu>();

        int pageNo = Integer.parseInt((pageNumber == null || pageNumber =="0") ? "1":pageNumber);
        int pageSize = Integer.parseInt((rowNumber == null || rowNumber =="0") ? "10":rowNumber);
        String orderBy = sortName == null  ? "updaterTime":sortName;
        String order = sortOrder == null  ? PageResults.DESC:sortOrder;

        PageResults<Menu> pageResults = new PageResults<Menu>();
        pageResults.setPageNo(pageNo);
        pageResults.setPageSize(pageSize);
        pageResults.setOrderBy(orderBy);
        pageResults.setOrder(order);

        menu = menuServices.getMenuByNum(menuName,(pageNo-1)*pageSize+1, pageNo*pageSize);

        long totalCount = menuServices.getMenuCount(menuName,(pageNo-1)*pageSize+1, pageNo*pageSize);

        pageResults.setResult(menu);
        pageResults.setTotalCount(totalCount);

        return Paging.ajaxGrid(pageResults);

    }

    @RequestMapping("/changeMenu")
    public String changeMenu(HttpServletRequest request,String id,String name,String pid,
                                  String type,String url,String icons,String orderNum){
        String sign = "";

        if( StringUtils.isEmpty(url) ){
            url = null;
        }

        //修改
        if(!StringUtils.isEmpty(id)){
            menuServices.updateLocalMenu(id,name,url,icons,orderNum,pid);
            sign = "edit";
        }else{
            //新增
            id = Guid.GenerateGUID();
            menuServices.creatMenu(id,name,url,icons,orderNum,pid);
            sign = "add";
        }

        return "redirect:addMenu?type=a&sign="+sign; //重定向
    }

    @ResponseBody
    @RequestMapping("/updateMenuState")
    public void updateMenuState(String id){
        menuServices.updateMenuState(id);
    }

}
