package com.xinshai.xinshai.controller;

import com.xinshai.xinshai.entiry.PageResults;
import com.xinshai.xinshai.model.Template;
import com.xinshai.xinshai.services.TemplateServices;
import com.xinshai.xinshai.util.Paging;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/open/template")
public class TemplateController {

    @Resource
    private TemplateServices templateServices;

    private String view = "template/";

    @RequestMapping("/templateList")
    public String templateList(){
        return view+"templateList";
    }

    @ResponseBody
    @RequestMapping("/templateTable")
    public Map<String,Object> templateTable(String pageNumber, String rowNumber, String sortName,
                                            String sortOrder, String meaning, HttpServletRequest request){

        List<Template> mb = new ArrayList<Template>();

        int pageNo = Integer.parseInt((pageNumber == null || pageNumber =="0") ? "1":pageNumber);
        int pageSize = Integer.parseInt((rowNumber == null || rowNumber =="0") ? "10":rowNumber);
        String orderBy = sortName == null  ? "updaterTime":sortName;
        String order = sortOrder == null  ? PageResults.DESC:sortOrder;

        PageResults<Template> pageResults = new PageResults<Template>();

        pageResults.setPageNo(pageNo);
        pageResults.setPageSize(pageSize);
        pageResults.setOrderBy(orderBy);
        pageResults.setOrder(order);

        mb = templateServices.templateTable(meaning,(pageNo-1)*pageSize+1, pageNo*pageSize);

        long totalCount = templateServices.getSignCount(meaning,(pageNo-1)*pageSize+1, pageNo*pageSize);

        pageResults.setResult(mb);
        pageResults.setTotalCount(totalCount);

        return Paging.ajaxGrid(pageResults);

    }

    @RequestMapping("/addTemplate")
    public String addTemplate(Model model,String id,String templateId, String first, String keyword1, String keyword2, String keyword3,
                              String keyword4,String keyword5,String remark,String keyCount, String meaning, String orderNum, String type, String sign){

        model.addAttribute("id",id);
        model.addAttribute("templateId",templateId);
        model.addAttribute("first",first);
        model.addAttribute("keyword1",keyword1);
        model.addAttribute("keyword2",keyword2);
        model.addAttribute("keyword3",keyword3);
        model.addAttribute("keyword4",keyword4);
        model.addAttribute("keyword5",keyword5);
        model.addAttribute("remark",remark);
        model.addAttribute("keyCount",keyCount);
        model.addAttribute("meaning",meaning);
        model.addAttribute("orderNum",orderNum);

        model.addAttribute("type",type);
        model.addAttribute("sign",sign);//标识是修改还是编辑

        return view+"addTemplate";
    }

    /**
     创建新模板
     */
    @RequestMapping("/createTemplate")
    public String createTemplate(String id,String templateId,String first,String keyword1,String keyword2,String keyword3,
                                 String keyword4,String keyword5,String remark,String keyCount, String meaning, String orderNum, String type){
        String sign = "";
        if("a".equals(type)){//新增
            templateServices.createCombine(id,templateId,first,keyword1,keyword2,keyword3,keyword4,keyword5,remark,keyCount,meaning,orderNum);
            sign = "add";
        }if("e".equals(type)){//修改
            templateServices.updateCombine(id,templateId,first,keyword1,keyword2,keyword3,keyword4,keyword5,remark,keyCount,meaning,orderNum);
            sign = "edit";
        }
        return "redirect:addTemplate?type=a&sign="+sign; //重定向
    }

    /**删除模板*/
    @ResponseBody
    @RequestMapping("/deleteRow")
    public void deleteRow(String id){
        templateServices.deleteRow(id);
    }


}
