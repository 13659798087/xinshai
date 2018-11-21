package com.xinshai.xinshai.services;

import com.xinshai.xinshai.dao.TemplateDao;
import com.xinshai.xinshai.model.Template;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TemplateServices {

    @Resource
    private TemplateDao templateDao;

    public List<Template> templateTable(String meaning, int pageNo, int pageSize) {
        return templateDao.templateTable(meaning,pageNo,pageSize);
    }

    public long getSignCount(String meaning, int pageNo, int pageSize) {
        return templateDao.getSignCount(meaning,pageNo,pageSize);
    }


    public void createCombine(String id,String templateId, String first, String keyword1, String keyword2, String keyword3, String keyword4,String keyword5,String remark,String keyCount, String meaning, String orderNum) {
        templateDao.createCombine(id,templateId,first,keyword1,keyword2,keyword3,keyword4,keyword5,remark,keyCount,meaning,orderNum);
    }

    public void updateCombine(String id,String templateId, String first, String keyword1, String keyword2, String keyword3, String keyword4,String keyword5,String remark,String keyCount, String meaning, String orderNum) {
        templateDao.updateCombine(id,templateId,first,keyword1,keyword2,keyword3,keyword4,keyword5,remark,keyCount,meaning,orderNum);
    }

    public void deleteRow(String id) {
        templateDao.deleteRow(id);
    }


    public Template getById(String id) {
        return templateDao.getById(id);
    }


}
