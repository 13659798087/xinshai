package com.xinshai.xinshai.controller;

import com.alibaba.fastjson.JSON;
import com.xinshai.xinshai.entiry.PageResults;
import com.xinshai.xinshai.model.Tag;
import com.xinshai.xinshai.model.WeixinUserInfo;
import com.xinshai.xinshai.services.RoleServices;
import com.xinshai.xinshai.services.UserRoleMenuServices;
import com.xinshai.xinshai.services.WeixinUserInfoServices;
import com.xinshai.xinshai.servlet.TokenThread;
import com.xinshai.xinshai.util.Paging;
import com.xinshai.xinshai.util.WeixinUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/open/weixinUser")
public class WeixinUserinfoController {

    @Resource
    private WeixinUserInfoServices weixinUserInfoServices;

    @Resource
    private UserRoleMenuServices userRoleMenuServices;

    @Resource
    private RoleServices roleServices;


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
                                          String sortOrder,String userName,String concerns,HttpServletRequest request){
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

        userInfo = weixinUserInfoServices.getUserMessage((pageNo-1)*pageSize+1, pageNo*pageSize,userName,concerns);
        long totalCount = weixinUserInfoServices.getUserCount((pageNo-1)*pageSize+1, pageNo*pageSize,userName,concerns);

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


    //批量拉取用户信息，即更新数据，将关注的用户信息拉去到数据库，当取消关注时，也将此表对应的openid的用户删除掉
    @ResponseBody
    @RequestMapping("/batch")
    public String batch() {

        //先删掉weixinUserinfo表所有数据，在插入新数据
        weixinUserInfoServices.deletaAll();

        String nextOpenid="";
        String openidList = null;
        openidList = WeixinUtil.getUserList( TokenThread.accessToken.getToken() , nextOpenid );
        String groupMessage = JSONObject.fromObject(WeixinUtil.initUserMessage(openidList)).toString();
        String result = WeixinUtil.getBatchUser(TokenThread.accessToken.getToken(),groupMessage);
        JSONArray array=JSONArray.fromObject(result);

        String nickname = null;//用户的昵称
        String sex = null;//用户的性别，值为1时是男性，值为2时是女性，值为0时是未知

        List<WeixinUserInfo> listUserInfo = new ArrayList<WeixinUserInfo>();


        for(int i=0;i<array.size();i++){

            WeixinUserInfo wu = new WeixinUserInfo();

            JSONObject job = array.getJSONObject(i);
            switch (job.getInt("sex")){
                case 1:
                    sex = "男";
                    break;
                case 2:
                    sex = "女";
                case 0:
                    sex = "未知";
                    break;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long time = Long.parseLong(job.getString("subscribe_time"));
            long lt = new Long(time);
            Date date = new Date(lt * 1000L);
            Timestamp t = Timestamp.valueOf( sdf.format(date)  );//关注的时间
            nickname = job.getString("nickname");
            //处理昵称中有空格的
            if(nickname.contains(" ")){
                nickname = nickname.replace(" ","&nbsp;");
            }

            wu.setOpenid( job.getString("openid") );
            wu.setNickname( nickname );
            wu.setSex(sex);
            wu.setLanguage( job.getString("language") );
            wu.setCity( job.getString("city") );
            wu.setProvince( job.getString("province") );
            wu.setCountry( job.getString("country") );
            wu.setGroupid( job.getString("groupid") );
            wu.setAttentionTime(t);

            listUserInfo.add(wu);
        }

        weixinUserInfoServices.insertBatchUser(listUserInfo);
       // weixinUserInfoServices.insertUserOpenid(openid,nickname,sex,language,city,province,country,groupid,t,remark);

        return "拉取用户列表成功，改接口最多拉取100条，可多次拉去，考录到前期关注的人少，先这样处理";
    }


}

