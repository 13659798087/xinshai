package com.xinshai.xinshai.services;

import com.xinshai.xinshai.dao.PatientsDao;
import com.xinshai.xinshai.model.PersonBinding;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@Service
public class PatientsServices {

    @Resource
    private PatientsDao patientsDao;


    public PersonBinding validataPerson(String p_m_name, String p_tel1, String p_bithday) {
        return patientsDao.validataPerson(p_m_name,p_tel1,p_bithday);
    }

    public List<PersonBinding> getTenAndNoPass(Integer dayCount) {
        return patientsDao.getTenAndNoPass(dayCount);
    }

    public int updatePass(String id) {
        return patientsDao.updatePass(id);
    }


    public List<PersonBinding> getTenAndNoPass2(Integer dayCount) {
        return patientsDao.getTenAndNoPass2(dayCount);
    }

    public int updatePass2(String id) {
        return patientsDao.updatePass2(id);
    }
}
