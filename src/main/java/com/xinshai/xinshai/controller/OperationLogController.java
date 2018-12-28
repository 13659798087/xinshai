package com.xinshai.xinshai.controller;


import com.xinshai.xinshai.entiry.PageResults;
import com.xinshai.xinshai.model.LogManagement;
import com.xinshai.xinshai.model.OperationLog;
import com.xinshai.xinshai.services.OperationLogServices;
import com.xinshai.xinshai.util.Paging;
import org.apache.axis.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/operationLog")
public class OperationLogController {

   @Resource
   private OperationLogServices operationLogServices;

   private String view = "operationLog/";

   @RequestMapping("/operation")
   public String logger(){
      return view + "operation";
   }

   @ResponseBody
   @RequestMapping("/operationTable")
   public Map<String,Object> getLogger(String pageNumber, String rowNumber, String sortName,String sortOrder,
                                       String time1,String time2,String operationType,String content){

       Timestamp time_1 = null;
       Timestamp time_2 = null;
       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       if(!StringUtils.isEmpty(time1)){
           time_1 = Timestamp.valueOf(time1 +" 00:00:00");
       }
       if(!StringUtils.isEmpty(time2)){
           time_2 = Timestamp.valueOf(time2 +" 23:59:59");
       }

      List<OperationLog> operationLog = new ArrayList<OperationLog>();
      int pageNo = Integer.parseInt((pageNumber == null || pageNumber =="0") ? "1":pageNumber);
      int pageSize = Integer.parseInt((rowNumber == null || rowNumber =="0") ? "10":rowNumber);
      String orderBy = sortName == null  ? "updaterTime":sortName;
      String order = sortOrder == null  ? PageResults.DESC:sortOrder;

      PageResults<OperationLog> pageResults = new PageResults<OperationLog>();
      pageResults.setPageNo(pageNo);
      pageResults.setPageSize(pageSize);
      pageResults.setOrderBy(orderBy);
      pageResults.setOrder(order);

      operationLog = operationLogServices.operationTable((pageNo-1)*pageSize+1,pageNo*pageSize,time_1,time_2,operationType,content);

      long totalCount = operationLogServices.getoperationCount((pageNo-1)*pageSize+1,pageNo*pageSize,time_1,time_2,operationType,content);

      pageResults.setResult(operationLog);
      pageResults.setTotalCount(totalCount);

      return Paging.ajaxGrid(pageResults);
   }


    @ResponseBody
    @RequestMapping("/deleteLog")
    public void deleteLog(HttpServletRequest request,String list){
        String[] array = list.split(",");
        for(String id : array) {
            operationLogServices.deleteLog(id);
        }
    }


}
