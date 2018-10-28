package com.xinshai.xinshai.controller;

import com.xinshai.xinshai.entiry.PageResults;
import com.xinshai.xinshai.model.UserInfo;
import com.xinshai.xinshai.model.UserRoleMenu;
import com.xinshai.xinshai.services.RoleServices;
import com.xinshai.xinshai.services.UserRoleMenuServices;
import com.xinshai.xinshai.services.UserServices;
import com.xinshai.xinshai.util.Guid;
import com.xinshai.xinshai.util.Paging;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/userPage")
public class UserController {

    @Resource
    private UserServices userServices;

    @Resource
    private UserRoleMenuServices userRoleMenuServices;

    @Resource
    private RoleServices roleServices;

    private String view = "userPage/";

    //UserController.class必须针对当前类，定义一个当前类的全局静态日志操作对象
    private static final Logger log= LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/getUserInfo")
    public String getUserInfo(){
        return view+"userInfo";
    }

    @ResponseBody
    @RequestMapping("/getUserRole")
    public Map<String,Object> getUserRole(String pageNumber, String rowNumber, String sortName,
                                          String sortOrder,String userName,String organizationName,HttpServletRequest request){
        //在缓存session中取医院的id
        String organizationId = (String) request.getSession().getAttribute("organizationId");
        String userId = (String) request.getSession().getAttribute("userId");

        List<UserInfo> userInfo = new ArrayList<UserInfo>();

        int pageNo = Integer.parseInt((pageNumber == null || pageNumber =="0") ? "1":pageNumber);
        int pageSize = Integer.parseInt((rowNumber == null || rowNumber =="0") ? "10":rowNumber);
        String orderBy = sortName == null  ? "updaterTime":sortName;
        String order = sortOrder == null  ? PageResults.DESC:sortOrder;

        PageResults<UserInfo> pageResults = new PageResults<UserInfo>();
        pageResults.setPageNo(pageNo);
        pageResults.setPageSize(pageSize);
        pageResults.setOrderBy(orderBy);
        pageResults.setOrder(order);

        userInfo = userServices.getUserMessage((pageNo-1)*pageSize+1, pageNo*pageSize,userName,organizationName);

        long totalCount = userServices.getUserCount((pageNo-1)*pageSize+1, pageNo*pageSize,userName,organizationName);

        pageResults.setResult(userInfo);
        pageResults.setTotalCount(totalCount);

        return Paging.ajaxGrid(pageResults);

    }

    @RequestMapping("/createUser")
    public String creatUser(HttpServletRequest request,String userId,String userName,String password,String dayLoginError,String organizationId,String roles){
        String sign = "";

        if(StringUtils.isEmpty(organizationId)){
            organizationId = (String) request.getSession().getAttribute("organizationId");
        }

        //修改
        if(!StringUtils.isEmpty(userId)){
            userServices.updateUser(userId,userName,password,dayLoginError,organizationId);
            //删除之前的关系后建立
            roleServices.deleteRela(userId);
            sign = "edit";
        }else{
            //新增
            userId = Guid.GenerateGUID();

            //当前创建人的userId,作为新创建记录的parentId,request.getSession().getAttribute("key")返回的是Object类型，
            // 要强转成字符串类型，在前面+(数据类型)
            String parentId = (String) request.getSession().getAttribute("userId");

            Integer dayLoginError1 = null;
            if (dayLoginError==null){
                dayLoginError1 = 0;
            }

            userServices.creatUser(userId,userName,password,dayLoginError1,organizationId,parentId);
            sign = "add";
        }
        String[] a = roles.split(",");
        for(int i=0;i<a.length;i++){
            String id = Guid.GenerateGUID();
            String roleId = a[i];
            roleServices.insertRela(id,userId,roleId);
        }

        return "redirect:addUser?type=a&sign="+sign; //重定向
    }

    @RequestMapping("/addUser")
    public String addUser(Model model,String userName,String password,String type,String organizationId,
                          String userId,String dayLoginError,String sign){

        model.addAttribute("userName",userName);
        model.addAttribute("password",password);
        model.addAttribute("organizationId",organizationId);
        model.addAttribute("userId",userId);
        model.addAttribute("dayLoginError",dayLoginError);

        model.addAttribute("type",type);
        model.addAttribute("sign",sign);//标识是修改还是编辑
        return view+"addUser";
    }


    @ResponseBody
    @RequestMapping("/validateUser")
    public Integer validateUser(HttpServletRequest request,String userName, String organizationId){

        int i = userServices.validateUser(userName,organizationId);
        if(i>0)
            i=1;
        else
            i=0;
        return i;
    }

    @ResponseBody
    @RequestMapping("/getRoleByUserId")
    public List<UserRoleMenu> getRoleByUserId(String userId){

        List<UserRoleMenu> listRole = roleServices.getRoleByUserId(userId);

        return listRole;
    }

    /**
     * 根据userId删除用户
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteUser")
    public void deleteUser(String userId){
        userServices.deleteUser(userId);
    }

    //修改密码页面
    @RequestMapping("/updatePassword")
    public String updatePassword(Model model,HttpServletRequest request,String x){
        String userName = (String) request.getSession().getAttribute("userName");
        UserInfo u = userServices.userloginMessage(userName);

        model.addAttribute("password",u.getPassword());
        model.addAttribute("userName",userName);

        if("0".equals(x)){
            model.addAttribute("signLogin","0");
        }

        return view+"updatePassword";
    }

    //确定修改密码
    @ResponseBody
    @RequestMapping("/sureChangePsw")
    public String sureChangePsw(Model model,String userName,String newPassword,String oldPassword){
        String a = "";
        int i =  userServices.changePsw(userName,newPassword,oldPassword);
        if(i==1)
            a="1";
        else
            a="0";

        return a;

    }


}

