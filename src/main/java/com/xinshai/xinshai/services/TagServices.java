package com.xinshai.xinshai.services;

import com.xinshai.xinshai.dao.TagDao;
import com.xinshai.xinshai.model.Tag;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TagServices {

    @Resource
    private TagDao tagDao;

    public void deleteAllTag() {
        tagDao.deleteAllTag();
    }

    public void insertTag(String id, String name, int count) {
        tagDao.insertTag(id, name, count);
    }

    public String getName(String id) {
        return tagDao.getName(id);
    }

    public List<Tag> getTagMessage(int pageNo, int pageSize, String name) {
        return tagDao.getTagMessage(pageNo, pageSize, name);
    }

    public long getTagCount(int pageNo, int pageSize, String name) {
        return tagDao.getTagCount(pageNo, pageSize, name);
    }

    public int validateTag(String name) {
        return tagDao.validateTag(name);
    }

    public void creaTag(String id, String name) {
        tagDao.creaTag(id,name);
    }


    public void updateTag(String id, String name) {
        tagDao.updateTag(id,name);
    }

    public void deleteTag(String id) {
        tagDao.deleteTag(id);
    }

    public Tag getTagById(String id) {
        return tagDao.getTagById(id);
    }

    public List<Tag> getAllTag() {
        return tagDao.getAllTag();
    }


}
