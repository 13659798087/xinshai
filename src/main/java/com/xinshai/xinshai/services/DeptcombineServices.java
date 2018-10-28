package com.xinshai.xinshai.services;

import com.xinshai.xinshai.dao.DeptcombineDao;
import com.xinshai.xinshai.model.Combine;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DeptcombineServices {

    @Resource
    private DeptcombineDao deptcombineDao;


    public List<Combine> getDeptCombine(Integer dcdId) {
        return deptcombineDao.getDeptCombine(dcdId);
    }

}
