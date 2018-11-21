package com.xinshai.xinshai.services;

import com.xinshai.xinshai.dao.MessageDao;
import com.xinshai.xinshai.model.MessagePush;
import com.xinshai.xinshai.model.UserInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MessageServices {

    @Resource
    private MessageDao messageDao;


    public List<MessagePush> getPushConfig(int pageNo, int pageSize,String meaning) {
        return messageDao.getPushConfig(pageNo,pageSize,meaning);
    }

    public long getPushConfigCount(String meaning) {
        return messageDao.getPushConfigCount(meaning);
    }


    public void updatePush(String id,String meaning, String dayCount) {
        messageDao.updatePush(id,meaning,dayCount);
    }

    public void deletePush(String id) {
        messageDao.deletePush(id);
    }

    public void createPush(String id, String meaning, String dayCount) {
        messageDao.createPush(id,meaning,dayCount);
    }

    public int getDayCount(int dayConutId) {
        return messageDao.getDayCount(dayConutId);
    }


}
