package com.xinshai.xinshai.services;

import com.xinshai.xinshai.dao.PersonalDataDao;
import com.xinshai.xinshai.model.PersonBinding;
import com.xinshai.xinshai.model.ResultPushMsg;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@Service
public class PersonalDataServices {

    @Resource
    private PersonalDataDao personalDataDao;

    public void createWeixinMsg(String id, String openId, String m_name, String bloodCard,String tel, String bithday) {
        personalDataDao.createWeixinMsg(id,openId,m_name,bloodCard,tel,bithday);
    }

    public List<PersonBinding> getMsgByOpenid(String openId) {
        return personalDataDao.getMsgByOpenid(openId);
    }

    public void deleteBind(String id) {
        personalDataDao.deleteBind(id);
    }

    public List<ResultPushMsg> getResultItem(String openId) {
        return personalDataDao.getResultItem(openId);
    }
}
