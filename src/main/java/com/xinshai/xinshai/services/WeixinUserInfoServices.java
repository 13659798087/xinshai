package com.xinshai.xinshai.services;

import com.xinshai.xinshai.dao.WeixinUserInfoDao;
import com.xinshai.xinshai.model.CheckinfoTest;
import com.xinshai.xinshai.model.WeixinMsg;
import com.xinshai.xinshai.model.WeixinUserInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@Service
public class WeixinUserInfoServices {

    @Resource
    private WeixinUserInfoDao weixinUserInfoDao;

    public List<WeixinUserInfo> getUserMessage(int pageNo, int pageSize, String userName, String organizationName) {
        return weixinUserInfoDao.getUserMessage(pageNo,pageSize,userName,organizationName);
    }

    public long getUserCount(int pageNo, int pageSize, String userName, String organizationName) {
        return weixinUserInfoDao.getUserCount(pageNo,pageSize,userName,organizationName);
    }

    public void insertUserOpenid(String openid, String nickname, String sex, String language, String city,
                                 String province, String country, String groupid, Timestamp attentionTime,
                                 String tagid_list,String remark) {
        weixinUserInfoDao.insertUserOpenid(openid,nickname,sex,language,city,province,country,groupid,attentionTime,tagid_list,remark);
    }


    public List<CheckinfoTest> getTest() {
        return weixinUserInfoDao.getTest();
    }

    public WeixinMsg getByApplyinfo(String m_name, String tel) {
        return weixinUserInfoDao.getByApplyinfo(m_name,tel);
    }

    public List<WeixinMsg> getAllMsg() {
        return weixinUserInfoDao.getAllMsg();
    }

    public void deleteUserInfo(String fromUserName) {
        weixinUserInfoDao.deleteUserInfo(fromUserName);
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
}
