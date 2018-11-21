package com.xinshai.xinshai.controller;

import com.xinshai.xinshai.entiry.PageResults;
import com.xinshai.xinshai.model.DynamicConfig;
import com.xinshai.xinshai.services.DynamicConfigServices;
import com.xinshai.xinshai.util.Guid;
import com.xinshai.xinshai.util.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/open/dynamicConfig")
public class DynamicConfigController {

    @Resource
    private DynamicConfigServices dynamicConfigServices;

    private String view = "dynamicConfig/";


    @Value("${myMessage}")
    private String myMessage;

    @Value("${bindingMsg}")
    private String bindingMsg;

    @Value("${searchMsg}")
    private String searchMsg;


    //UserController.class必须针对当前类，定义一个当前类的全局静态日志操作对象
    private static final Logger log= LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/dynamicConfig")
    public String config(){
        return view+"dynamicConfig";
    }

    @ResponseBody
    @RequestMapping("/getDynamic")
    public Map<String,Object> getDynamic(String pageNumber, String rowNumber, String sortName,
                                          String sortOrder, String userName, String organizationName, HttpServletRequest request){
        List<DynamicConfig> dynamicConfig = new ArrayList<DynamicConfig>();

        int pageNo = Integer.parseInt((pageNumber == null || pageNumber =="0") ? "1":pageNumber);
        int pageSize = Integer.parseInt((rowNumber == null || rowNumber =="0") ? "10":rowNumber);
        String orderBy = sortName == null  ? "updaterTime":sortName;
        String order = sortOrder == null  ? PageResults.DESC:sortOrder;

        PageResults<DynamicConfig> pageResults = new PageResults<DynamicConfig>();

        pageResults.setPageNo(pageNo);
        pageResults.setPageSize(pageSize);
        pageResults.setOrderBy(orderBy);
        pageResults.setOrder(order);

        dynamicConfig = dynamicConfigServices.getDynamic((pageNo-1)*pageSize+1, pageNo*pageSize);

        long totalCount = dynamicConfigServices.getDynamicCount((pageNo-1)*pageSize+1, pageNo*pageSize);
        pageResults.setResult(dynamicConfig);
        pageResults.setTotalCount(totalCount);

        return Paging.ajaxGrid(pageResults);
    }

    @RequestMapping("/addDynamic")
    public String addDynamic(HttpServletRequest request,Model model, String id, String page,String pageContent, String type){
        model.addAttribute("id",id);
        model.addAttribute("page",page);
        model.addAttribute("pageContent",pageContent);
        model.addAttribute("type",type);
        return view+"addDynamic";
    }


    @ResponseBody
    @RequestMapping(value="uploadPicture",headers="content-type=multipart/*" ,method= RequestMethod.POST)
    public Map uploadPicture(@RequestParam("file")MultipartFile file,String id,String page,String pageContent,String type)throws Exception{
        String sign = "";
        Map map=new HashMap<>();
        DynamicConfig dynamic = new DynamicConfig();
        String name = file.getOriginalFilename();
        long filesize  = file.getSize();

        if("a".equals(type)){//新增
            id = Guid.GenerateGUID();
            dynamic.setId(id);
            dynamic.setPage(page);
            dynamic.setPageContent(pageContent);
            dynamic.setImage(file.getBytes());

            dynamicConfigServices.createDynamic(id,page,pageContent,file.getBytes());

            sign = "add";
        }if("e".equals(type)){//修改
            if(filesize==0){
                dynamicConfigServices.updateDynamic1(id,page,pageContent);
            }else{
                dynamicConfigServices.updateDynamic(id,page,pageContent,file.getBytes());
            }
            sign = "edit";
        }

        map.put("id",id);
        map.put("sign",sign);

        return map;
    }

    @RequestMapping(value = "Pictureshows")
    public ResponseEntity<byte[]> Pictureshow(String id) {

        DynamicConfig list = dynamicConfigServices.Pictureshows(id);//根据员工号查找头像

        byte[] bytes=null;
        if(list!=null){
            bytes=list.getImage();
        }
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentDispositionFormData("attachment", new String("图片".getBytes("GBK"),"ISO8859-1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(bytes,headers, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value="/deleteImage")
    public void deleteImage(String id){
        dynamicConfigServices.deleteImage(id);
    }


    @ResponseBody
    @RequestMapping(value="/getMyMessage")
    public String getMyMessage(){
        return dynamicConfigServices.getMyMessage(myMessage);
    }
    @ResponseBody
    @RequestMapping(value="/bindingMsg")
    public String bindingMsg(){
        return dynamicConfigServices.getMyMessage(bindingMsg);
    }
    @ResponseBody
    @RequestMapping(value="/searchMsg")
    public String searchMsg(){
        return dynamicConfigServices.getMyMessage(searchMsg);
    }

}
