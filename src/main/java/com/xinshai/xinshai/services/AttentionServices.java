package com.xinshai.xinshai.services;

import com.xinshai.xinshai.dao.AttentionDao;
import com.xinshai.xinshai.model.AttentionReply;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AttentionServices {

    @Resource
    private AttentionDao attentionDao;


    public List<AttentionReply> getAttentionReply() {
        return attentionDao.getAttentionReply();
    }

    public long getReplyCount() {
        return attentionDao.getReplyCount();
    }

    public void createReply(String id, String content, String orderNum) {
        attentionDao.createReply(id, content, orderNum);
    }

    public void updateReply(String id, String content, String orderNum) {
        attentionDao.updateReply(id, content, orderNum);
    }

    public void deleteReply(String id) {
        attentionDao.deleteReply(id);
    }



}
