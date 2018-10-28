package com.xinshai.xinshai.services;


import com.xinshai.xinshai.dao.MbTypeDao;
import com.xinshai.xinshai.model.MbType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MbTypeServices {

    @Resource
    private MbTypeDao mbTypeDao;

    public List<MbType> getMbType() {
        return mbTypeDao.getMbType();
    }

    public MbType getMbTypeById(String id) {
        return mbTypeDao.getMbTypeById(id);
    }


}
