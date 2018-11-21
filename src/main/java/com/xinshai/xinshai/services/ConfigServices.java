package com.xinshai.xinshai.services;

import com.xinshai.xinshai.dao.ConfigDao;
import com.xinshai.xinshai.model.Config;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ConfigServices {

    @Resource
    private ConfigDao configDao;

    public List<Config> configTable(String cf_code, int pageNo, int pageSize) {
        return configDao.configTable(cf_code, pageNo, pageSize);
    }

    public long getConfigCount(String cf_code, int pageNo, int pageSize) {
        return configDao.getConfigCount(cf_code, pageNo, pageSize);
    }

    public int validateName(String cf_code) {
        return configDao.validateName(cf_code);
    }

    public void createConfig(String cf_code, Integer cf_flag, String cf_val, String cf_explain,String orderNum) {
        configDao.createConfig(cf_code,cf_flag,cf_val,cf_explain,orderNum);
    }

    public void updateConfig(String cf_code, Integer cf_flag, String cf_val, String cf_explain,String orderNum) {
        configDao.updateConfig(cf_code,cf_flag,cf_val,cf_explain,orderNum);
    }

    public void deleteRow(String cf_code) {
        configDao.deleteRow(cf_code);
    }

    public List<Config> getAllConfig() {
        return configDao.getAllConfig();
    }


}
