package com.xinshai.xinshai.services;

import com.xinshai.xinshai.dao.UserRoleMenuDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UserRoleMenuServices {

    @Resource
    private UserRoleMenuDao userRoleMenuDao;

    public List<Map<String, Object>> getLoginMessage(String userName) {
        return userRoleMenuDao.getLoginMessage(userName);
    }


}
