package com.xinshai.xinshai.services;

import com.xinshai.xinshai.dao.WeixinUserInfoDao;
import com.xinshai.xinshai.model.CheckinfoTest;
import com.xinshai.xinshai.model.PersonBinding;
import com.xinshai.xinshai.model.ResultPushMsg;
import com.xinshai.xinshai.model.WeixinUserInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@Service
public class WeixinUserInfoServices {

    @Resource
    private WeixinUserInfoDao weixinUserInfoDao;

    public List<WeixinUserInfo> getUserMessage(int pageNo, int pageSize, String userName, String concerns) {
        return weixinUserInfoDao.getUserMessage(pageNo,pageSize,userName,concerns);
    }

    public long getUserCount(int pageNo, int pageSize, String userName, String concerns) {
        return weixinUserInfoDao.getUserCount(pageNo,pageSize,userName,concerns);
    }

    public void insertUserOpenid(String openid, String nickname, String sex, String language, String city,
                                 String province, String country, String groupid, Timestamp attentionTime,
                                String remark) {
        weixinUserInfoDao.insertUserOpenid(openid,nickname,sex,language,city,province,country,groupid,attentionTime,remark);
    }


    public List<CheckinfoTest> getTest() {
        return weixinUserInfoDao.getTest();
    }

    public PersonBinding getByApplyinfo(String m_name, String tel) {
        return weixinUserInfoDao.getByApplyinfo(m_name,tel);
    }

    public List<ResultPushMsg> getAllMsg() {
        return weixinUserInfoDao.getAllMsg();
    }

    public void UpdateConcerns(String fromUserName,int concerns) {
        weixinUserInfoDao.UpdateConcerns(fromUserName,concerns);
    }

    public void deleteWeixinMsg(String fromUserName) {
        weixinUserInfoDao.deleteWeixinMsg(fromUserName);
    }

    public void deletaAll() {
        weixinUserInfoDao.deletaAll();
    }

    public void updateLocalRemark(String openid, String remark) {
        weixinUserInfoDao.updateLocalRemark(openid,remark);
    }

    public void updateTag(String openid, String tagid1) {
        weixinUserInfoDao.updateTag(openid,tagid1);
    }

    public void bindMessage(String openId) {
        weixinUserInfoDao.bindMessage(openId);
    }

    public int getByOpenid(String openid) {
        return  weixinUserInfoDao.getByOpenid(openid);
    }

    public List<WeixinUserInfo> getRecentlyUser(Integer dayCount) {
        return  weixinUserInfoDao.getRecentlyUser(dayCount);
    }

    public void AlreadyPushMsg(String value) {
        weixinUserInfoDao.AlreadyPushMsg(value);
    }

    public void deleteResult() {
        weixinUserInfoDao.deleteResult();
    }
}
