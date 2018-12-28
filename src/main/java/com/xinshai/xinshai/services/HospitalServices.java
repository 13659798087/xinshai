package com.xinshai.xinshai.services;

import com.xinshai.xinshai.dao.HospitalDao;
import com.xinshai.xinshai.model.Hospital;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HospitalServices {

    @Resource
    private HospitalDao hospitalDao;

    public List<Hospital> getLocation() {
        return hospitalDao.getLocation();
    }

    public long getLocationCount() {
        return hospitalDao.getLocationCount();
    }


    public void updateLocation( String h_name, String h_address_name, String h_address, String h_latitude,String h_longitude,
                                 String h_scale, String h_infoUrl,String weixinName,String appid,String appsecret) {
        hospitalDao.updateLocation(h_name,h_address_name,h_address,h_latitude,h_longitude,h_scale,h_infoUrl,weixinName,appid,appsecret);
    }

    public void deleteById(String id) {
        hospitalDao.deleteById(id);
    }

    public Hospital getLocationById() {
        return hospitalDao.getLocationById();
    }

    public String getAppid() {
        return hospitalDao.getAppid();
    }

    public String getAppsecret() {
        return hospitalDao.getAppsecret();
    }

    public String getDomainUrl() {
        return hospitalDao.getDomainUrl();
    }
}
