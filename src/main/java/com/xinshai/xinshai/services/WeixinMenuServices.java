package com.xinshai.xinshai.services;

import com.xinshai.xinshai.dao.WeixinMenuDao;
import com.xinshai.xinshai.model.WeixinMenu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WeixinMenuServices {

    @Resource
    private WeixinMenuDao weixinMenuDao;


    public List<WeixinMenu> getWeixinMenu() {
        return weixinMenuDao.getWeixinMenu();
    }

    public void updateLocalMenu(String id, String name, String type, String url, String menukey, String orderNum,String pid) {
        weixinMenuDao.updateLocalMenu(id,name,type,url,menukey,orderNum,pid);
    }

    public void creatMenu(String id,String name,String type,String url,String menukey,String orderNum,String pid) {
        weixinMenuDao.creatMenu(id,name,type,url,menukey,orderNum,pid);
    }


    public List<WeixinMenu> parentList() {
        return weixinMenuDao.parentList();
    }

    public void updateMenuState(String id) {
        weixinMenuDao.updateMenuState(id);
    }
}
