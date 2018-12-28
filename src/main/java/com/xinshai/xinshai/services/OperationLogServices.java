package com.xinshai.xinshai.services;

import com.xinshai.xinshai.dao.OperationLogDao;
import com.xinshai.xinshai.model.OperationLog;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@Service
public class OperationLogServices {

    @Resource
    private OperationLogDao operationLogDao;
/*
    public void recordLog(String operationId,String operationType,String systemError,String responseTime) {
        operationLogDao.recordLog(operationId,operationType,systemError,responseTime);
    }*/


    public List<OperationLog> operationTable(int pageNo,int pageSize,Timestamp time_1,Timestamp time_2,String operationType,String content) {
        return operationLogDao.operationTable(pageNo,pageSize,time_1,time_2,operationType,content);
    }

    public long getoperationCount(int pageNo, int pageSize,Timestamp time_1,Timestamp time_2,String operationType,String content) {
        return operationLogDao.getoperationCount(pageNo,pageSize,time_1,time_2,operationType,content);
    }

    public void recordLog(String operationId, String operationType, String content, String openId, String deviceName, String ipAddress) {
        operationLogDao.recordLog(operationId,operationType,content,openId,deviceName,ipAddress);
    }

    public void deleteLog(String id) {
        operationLogDao.deleteLog(id);
    }
}
