package com.xinshai.xinshai.services;

import com.xinshai.xinshai.dao.ResultPushMsgDao;
import com.xinshai.xinshai.entiry.CheckInfoPushMsg;
import com.xinshai.xinshai.entiry.ListResult;
import com.xinshai.xinshai.entiry.ReportMsg;
import com.xinshai.xinshai.entiry.WaitPushMsg;
import com.xinshai.xinshai.model.WeixinUserInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ResultPushMsgServices {

    @Resource
    private ResultPushMsgDao resultPushMsgDao;

    public void insertBindRemind(String addInsert) {
        resultPushMsgDao.insertBindRemind(addInsert);
    }

    public void pushHistory(String addInsert) {
        resultPushMsgDao.pushHistory(addInsert);
    }

    public void pushFail(String wrongId) {
        resultPushMsgDao.pushFail(wrongId);
    }

    public void deleteSuccessId(String successId) {
        resultPushMsgDao.deleteSuccessId(successId);
    }

    public Boolean insertBatchCode(List<WeixinUserInfo> result) {
        return resultPushMsgDao.insertBatchCode(result);
    }

    public void insertBatchHistory(List<ListResult> result2, int i) {
        resultPushMsgDao.insertBatchHistory(result2,i);
    }

    public List<ListResult> getPushResult() {
        return resultPushMsgDao.getPushResult();
    }

    public List<ReportMsg> getReportByOpenId(String openId) {
        return resultPushMsgDao.getReportByOpenId(openId);
    }

    public void updateFlag(String successId) {
        resultPushMsgDao.updateFlag(successId);
    }

    public List<ListResult> get1() {
        return resultPushMsgDao.get1();
    }

    public List<CheckInfoPushMsg> getCheckinfoPatientId(String patientIdList) {
        return resultPushMsgDao.getCheckinfoPatientId(patientIdList);
    }


    public List<WaitPushMsg> waitSendMsg(int pageNo,int pageSize, String p_name,String first,String type) {
        return resultPushMsgDao.waitSendMsg(pageNo,pageSize,p_name,first,type);
    }

    public long waitSendMsgCount(String p_name, String first,String type) {
        return resultPushMsgDao.waitSendMsgCount(p_name,first,type);
    }

    public void deleteSendMsg(String id) {
        resultPushMsgDao.deleteSendMsg(id);
    }


    public void updateMsg(String id, String openId, String patientId, String tid, String first, String keyword1, String keyword2,
                          String keyword3, String keyword4, String keyword5, String remark) {
        resultPushMsgDao.updateMsg(id,openId,patientId,tid,first,keyword1,keyword2,keyword3,keyword4,keyword5,remark);
    }

    public void creatMsg(String id,String openId,String patientId,String tid,String first,String keyword1,String keyword2,
                         String keyword3,String keyword4,String keyword5, String remark) {

        resultPushMsgDao.creatMsg(id,openId,patientId,tid,first,keyword1,keyword2,keyword3,keyword4,keyword5,remark);
    }

    public List<ListResult> getListId(String listId) {
        return resultPushMsgDao.getListId(listId);
    }
}
