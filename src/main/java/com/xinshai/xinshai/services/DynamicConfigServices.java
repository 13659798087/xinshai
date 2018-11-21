package com.xinshai.xinshai.services;

import com.xinshai.xinshai.dao.DynamicConfigDao;
import com.xinshai.xinshai.model.DynamicConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DynamicConfigServices {

    @Resource
    private DynamicConfigDao dynamicConfigDao;


    public List<DynamicConfig> getDynamic(int pageNo,int pageSize) {
        return dynamicConfigDao.getDynamic(pageNo,pageSize);
    }

    public long getDynamicCount(int pageNo,int pageSize) {
        return dynamicConfigDao.getDynamicCount(pageNo,pageSize);
    }

    public void updateDynamic(String id, String page, String pageContent, byte[] bytes) {
        dynamicConfigDao.updateDynamic(id,page,pageContent,bytes);
    }

    public void createDynamic(String id, String page, String pageContent, byte[] bytes) {
        dynamicConfigDao.createDynamic(id,page,pageContent,bytes);
    }

    public DynamicConfig Pictureshows(String id) {
        return dynamicConfigDao.Pictureshows(id);
    }

    public void deleteImage(String id) {
        dynamicConfigDao.deleteImage(id);
    }

    public String getMyMessage(String id) {
        return dynamicConfigDao.getMyMessage(id);
    }

    public void updateDynamic1(String id, String page, String pageContent) {
        dynamicConfigDao.updateDynamic1(id,page,pageContent);
    }


}
