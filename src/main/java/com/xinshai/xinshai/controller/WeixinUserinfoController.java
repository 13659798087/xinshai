package com.xinshai.xinshai.controller;

import com.alibaba.fastjson.JSON;
import com.xinshai.xinshai.entiry.PageResults;
import com.xinshai.xinshai.model.Tag;
import com.xinshai.xinshai.model.WeixinUserInfo;
import com.xinshai.xinshai.services.RoleServices;
import com.xinshai.xinshai.services.TagServices;
import com.xinshai.xinshai.services.UserRoleMenuServices;
import com.xinshai.xinshai.services.WeixinUserInfoServices;
import com.xinshai.xinshai.servlet.TokenThread;
import com.xinshai.xinshai.util.Paging;
import com.xinshai.xinshai.util.WeixinUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/open/weixinUser")
public class WeixinUserinfoController {

    @Resource
    private WeixinUserInfoServices weixinUserInfoServices;

    @Resource
    private UserRoleMenuServices userRoleMenuServices;

    @Resource
    private RoleServices roleServices;

    @Resource
    private TagServices tagServices;

    private String view = "weixinUser/";

    //UserController.class必须针对当前类，定义一个当前类的全局静态日志操作对象
    private static final Logger log= LoggerFactory.getLogger(WeixinUserinfoController.class);

    @RequestMapping("/userList")
    public String getUserInfo(){
        return view+"userList";
    }

    @ResponseBody
    @RequestMapping("/getUserInfo")
    public Map<String,Object> getUserRole(String pageNumber, String rowNumber, String sortName,
                                          String sortOrder,String userName,String organizationName,HttpServletRequest request){
        List<WeixinUserInfo> userInfo = new ArrayList<WeixinUserInfo>();

        int pageNo = Integer.parseInt((pageNumber == null || pageNumber =="0") ? "1":pageNumber);
        int pageSize = Integer.parseInt((rowNumber == null || rowNumber =="0") ? "10":rowNumber);
        String orderBy = sortName == null  ? "updaterTime":sortName;
        String order = sortOrder == null  ? PageResults.DESC:sortOrder;

        PageResults<WeixinUserInfo> pageResults = new PageResults<WeixinUserInfo>();
        pageResults.setPageNo(pageNo);
        pageResults.setPageSize(pageSize);
        pageResults.setOrderBy(orderBy);
        pageResults.setOrder(order);

        userInfo = weixinUserInfoServices.getUserMessage((pageNo-1)*pageSize+1, pageNo*pageSize,userName,organizationName);
        long totalCount = weixinUserInfoServices.getUserCount((pageNo-1)*pageSize+1, pageNo*pageSize,userName,organizationName);

        String tagName = "";
        for(WeixinUserInfo u : userInfo){
            if(u.getTagid_list() != null){
                String tid = u.getTagid_list().replace("[","").replace("]","");
                if(tid.contains(",")){//说明含有两个以上标签
                    String[] A = tid.split(",");
                    tagName = "";
                    for(int i=0;i<A.length;i++){
                        tagName += "["+ tagServices.getName(A[i])+"]";
                    }
                }else{//含有一个标签
                    tagName = "["+ tagServices.getName(tid)+"]";
                }
                u.setTagName_list(tagName);
            }
        }
        pageResults.setResult(userInfo);
        pageResults.setTotalCount(totalCount);
        return Paging.ajaxGrid(pageResults);
    }


    @RequestMapping("/updateUserMsg")
    public String updateUserMsg(String nickname,String openid,String remark,String newTagid,String oldTagid) throws UnsupportedEncodingException {
        String sign = "edit";
        String tagid1 = "";
        if(!StringUtils.isEmpty(newTagid)){
            tagid1 = "[" + newTagid +"]";
        }

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("openid",openid);
        map.put("remark",remark);

        String remark1 = JSON.toJSONString(map);
        int result = WeixinUtil.updateFarRemark( TokenThread.accessToken.getToken(),remark1);

        if(result==0){//远程微信后台数据已更改，再更新数据库
            weixinUserInfoServices.updateLocalRemark(openid,remark);
        }

        String oldTag = null;
        if(oldTagid.equals("null")){

        }else{
            oldTag = oldTagid.replace("[","").replace("]","");
            int result1 = 0;
            if(!oldTag.contains(",")){
                Tag tag = new Tag();
                List<String> list = new ArrayList<String>();
                list.add(openid);
                tag.setOpenid_list(list);
                tag.setTagid(oldTag);
                String ta = JSON.toJSONString(tag);

                //先删除用户拥有的标签，再给用户加标签
                result1 = WeixinUtil.canceltag( TokenThread.accessToken.getToken(),ta);
            }else{
                String[] a = oldTag.split(",");
                for(int i=0;i<a.length;i++){

                    Tag tag = new Tag();
                    List<String> list = new ArrayList<String>();
                    list.add(openid);
                    tag.setOpenid_list(list);
                    tag.setTagid(a[i]);
                    String ta = JSON.toJSONString(tag);

                    result1 = WeixinUtil.canceltag( TokenThread.accessToken.getToken(),ta);
                }
            }

        }

        //加标签
        int result2=0;

        if(StringUtils.isEmpty(newTagid)){//新的为空
            Tag tag = new Tag();
            List<String> list = new ArrayList<String>();
            list.add(openid);
            tag.setOpenid_list(list);
            tag.setTagid(oldTag);
            String ta = JSON.toJSONString(tag);
            result2 = WeixinUtil.canceltag( TokenThread.accessToken.getToken(),ta);
            tagid1=null;
        }
        else if(!newTagid.contains(",")){

            Tag tag = new Tag();
            List<String> list = new ArrayList<String>();
            list.add(openid);
            tag.setOpenid_list(list);
            tag.setTagid(newTagid);
            String ta = JSON.toJSONString(tag);
            //String n = "{\"openid_list\" : [\"ocYxcuAEy30bX0NXmGn4ypqx3tI0\",\"ocYxcuBt0mRugKZ7tGAHPnUaOW7Y\"], \"tagid\" : 134 }";

            result2 = WeixinUtil.batchtagging( TokenThread.accessToken.getToken(),ta);
        }else{
            String[] a = newTagid.split(",");
            for(int i=0;i<a.length;i++){

                Tag tag = new Tag();
                List<String> list = new ArrayList<String>();
                list.add(openid);
                tag.setOpenid_list(list);
                tag.setTagid(a[i]);
                String ta = JSON.toJSONString(tag);
                result2 = WeixinUtil.batchtagging( TokenThread.accessToken.getToken(),ta);
            }
        }

        if(result2==0){
            weixinUserInfoServices.updateTag(openid,tagid1);
        }

        return "redirect:addUser?type=e&sign="+sign+"&nickname="+ URLEncoder.encode(nickname,"UTF-8") +"&remark="
                + URLEncoder.encode(remark,"UTF-8")+"&openid="+openid+"&tagid_list="+newTagid;           //重定向
    }



    @RequestMapping("/addUser")
    public String addUser(Model model,String nickname,String remark,String openid,String type,String tagid_list,String sign){

        model.addAttribute("nickname",nickname);
        model.addAttribute("remark",remark);
        model.addAttribute("tagid_list",tagid_list);
        model.addAttribute("openid",openid);

        model.addAttribute("type",type);
        model.addAttribute("sign",sign);//标识是修改还是编辑
        return view+"addUser";
    }


    /*@ResponseBody
    @RequestMapping("/validateUser")
    public Integer validateUser(HttpServletRequest request,String userName, String organizationId){

        int i = userServices.validateUser(userName,organizationId);
        if(i>0)
            i=1;
        else
            i=0;
        return i;
    }*/



}

