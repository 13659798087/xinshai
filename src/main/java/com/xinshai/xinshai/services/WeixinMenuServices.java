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


}
