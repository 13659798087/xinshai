package com.xinshai.xinshai.services;

import com.xinshai.xinshai.dao.ResultPushMsgDao;
import com.xinshai.xinshai.entiry.ListResult;
import com.xinshai.xinshai.model.ResultPushMsg;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ResultPushMsgServices {

    @Resource
    private ResultPushMsgDao resultPushMsgDao;


    public List<ListResult> getPushResult(Integer pushCountId) {
        return resultPushMsgDao.getPushResult(pushCountId);
    }

    public void insertBindRemind(String id, String openid, String template1, int i) {
        resultPushMsgDao.insertBindRemind(id,openid,template1,i);
    }
}
