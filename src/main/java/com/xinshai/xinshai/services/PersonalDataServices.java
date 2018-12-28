package com.xinshai.xinshai.services;

import com.xinshai.xinshai.dao.PersonalDataDao;
import com.xinshai.xinshai.entiry.WinxinPerson;
import com.xinshai.xinshai.model.PersonBinding;
import com.xinshai.xinshai.model.Signpic;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    public Signpic get1() {
        return personalDataDao.get1();
    }

    public void createpersonlMsg(String id, String openId, String m_name, String bloodCard, String tel, String bithday,
                                 String tes,String hospitalNo,String m_age,String f_name,String tel_2,String address,String pregnanciesNum,
                                 String deliverNum,String pregnancyWeek,String pregnancyDay,String sex,String weigh) {
        personalDataDao.createpersonlMsg(id,openId,m_name,bloodCard,tel,bithday,tes,hospitalNo,m_age,f_name,tel_2,address,pregnanciesNum,deliverNum,pregnancyWeek,pregnancyDay,sex,weigh);
    }

    public void insertBatchPersonMsg(List<PersonBinding> list) {
        personalDataDao.insertBatchPersonMsg(list);
    }

    public List<PersonBinding> getPatientList(String openId) {
        return personalDataDao.getPatientList(openId);
    }

    public List<WinxinPerson> getPersonReport(String patientId) {
        return personalDataDao.getPersonReport(patientId);
    }


    public String queryPatientId(String bloodCard, String m_name, String bithday) {
        return personalDataDao.queryPatientId(bloodCard,m_name,bithday);
    }
}
