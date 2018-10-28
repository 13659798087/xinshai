package com.xinshai.xinshai.services;

import com.xinshai.xinshai.dao.PersonalDataDao;
import com.xinshai.xinshai.model.WeixinMsg;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@Service
public class PersonalDataServices {

    @Resource
    private PersonalDataDao personalDataDao;

    public void createWeixinMsg(String id, String openId, String p_name, String p_tel, Timestamp s_birthday) {
        personalDataDao.createWeixinMsg(id,openId,p_name,p_tel,s_birthday);
    }

    public List<WeixinMsg> getMsgByOpenid(String openId) {
        return personalDataDao.getMsgByOpenid(openId);
    }

    public void deleteBind(String id) {
        personalDataDao.deleteBind(id);
    }

}
