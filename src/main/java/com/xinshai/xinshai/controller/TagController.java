package com.xinshai.xinshai.controller;

import com.alibaba.fastjson.JSON;
import com.xinshai.xinshai.entiry.PageResults;
import com.xinshai.xinshai.model.Tag;
import com.xinshai.xinshai.services.TagServices;
import com.xinshai.xinshai.servlet.TokenThread;
import com.xinshai.xinshai.util.Paging;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/open/userTag")
public class TagController {

    @Resource
    private TagServices tagServices;

    private String view = "userTag/";

    @RequestMapping("/getTagList")
    public String getTagList(){
        return view+"getTagList";
    }

    @ResponseBody
    @RequestMapping("/getTableTag")
    public Map<String,Object> getTableTag(String pageNumber, String rowNumber, String sortName,
                                          String sortOrder, String name, HttpServletRequest request){

        List<Tag> listTag = new ArrayList<Tag>();

        int pageNo = Integer.parseInt((pageNumber == null || pageNumber =="0") ? "1":pageNumber);
        int pageSize = Integer.parseInt((rowNumber == null || rowNumber =="0") ? "10":rowNumber);
        String orderBy = sortName == null  ? "updaterTime":sortName;
        String order = sortOrder == null  ? PageResults.DESC:sortOrder;

        PageResults<Tag> pageResults = new PageResults<Tag>();
        pageResults.setPageNo(pageNo);
        pageResults.setPageSize(pageSize);
        pageResults.setOrderBy(orderBy);
        pageResults.setOrder(order);

        listTag = tagServices.getTagMessage((pageNo-1)*pageSize+1, pageNo*pageSize,name);

        long totalCount = tagServices.getTagCount((pageNo-1)*pageSize+1, pageNo*pageSize,name);

        pageResults.setResult(listTag);
        pageResults.setTotalCount(totalCount);

        return Paging.ajaxGrid(pageResults);
    }

    @RequestMapping("/addTag")
    public String addUser(Model model,String id,String count,String name,String type,String sign){

        model.addAttribute("id",id);
        model.addAttribute("name",name);
        model.addAttribute("count",count);

        model.addAttribute("type",type);
        model.addAttribute("sign",sign);//标识是修改还是编辑
        return view+"addTag";
    }

    @RequestMapping("/createTag")
    public String createTag(HttpServletRequest request,String id,String name){
        Tag tag = new Tag();
        Map<String,Object> map = new HashMap<String,Object>();
        String sign = "";

        //修改
        if(!StringUtils.isEmpty(id)){
            map.put("id",id);
            map.put("name",name);
            tag.setTag(map);
            String msg1 = JSON.toJSONString(tag);

            int result = WeixinUtil.updateTag( TokenThread.accessToken.getToken(), msg1);
            if(result==0){
                tagServices.updateTag(id,name);
            }
            sign = "edit";

        }else{
            //新增
            map.put("name",name);
            tag.setTag(map);

            String msg2 = JSON.toJSONString(tag);
            String result = WeixinUtil.createTag( TokenThread.accessToken.getToken(), msg2);

            JSONObject json = JSONObject.fromObject(result);

            String tid = json.getString("id");
            String tname = json.getString("name");
            tagServices.creaTag(tid,tname);
            sign = "add";
        }

        return "redirect:addTag?type=a&sign="+sign; //重定向
    }

    //删除标签
    @ResponseBody
    @RequestMapping("/deleteTag")
    public void deleteTag(String id){
        Tag tag = new Tag();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",id);
        tag.setTag(map);
        String msg = JSON.toJSONString(tag);
        int result = WeixinUtil.deleteTag( TokenThread.accessToken.getToken(), msg);
        if(result==0){
            tagServices.deleteTag(id);
        }
    }

    @ResponseBody
    @RequestMapping("/validateTag")
    public Integer validateTag(HttpServletRequest request,String name){

        int i = tagServices.validateTag(name);
        if(i>0)
            i=1;
        else
            i=0;
        return i;
    }

    @ResponseBody
    @RequestMapping("/byTagId")
    public List<Tag> byTagId(String tagid_list){
        List<Tag> listTag = new ArrayList<Tag>();
        Tag tag = new Tag();
        String aa = tagid_list.replace("[","").replace("]","");
        if(!aa.contains(",")){
            tag = tagServices.getTagById(aa);
            listTag.add(tag);
        }else{
            String[] bb = aa.split(",");
            for(int i=0;i<bb.length;i++){
                tag = tagServices.getTagById(bb[i]);
                listTag.add(tag);
            }
        }
        return listTag;
    }


    @ResponseBody
    @RequestMapping("/getAllTag")
    public List<Tag> getAllTag(String tagid_list){
        List<Tag> listTag = new ArrayList<Tag>();
        listTag = tagServices.getAllTag();
        return listTag;
    }

}
