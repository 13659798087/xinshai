package com.xinshai.xinshai.services;


import com.xinshai.xinshai.dao.MenuDao;
import com.xinshai.xinshai.model.Menu;
import com.xinshai.xinshai.model.UserRoleMenu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class MenuServices {

    @Resource
    private MenuDao menuDao;

    public List<Menu> getAllMenu() {
        return menuDao.getAllMenu();
    }


    public List<Menu> getMenuByNum(String menuName, int pageNo, int pageSize) {
        return menuDao.getMenuByNum(menuName,pageNo,pageSize);
    }

    public long getMenuCount(String menuName, int pageNo, int pageSize) {
        return menuDao.getMenuCount(menuName,pageNo,pageSize);
    }

    public void insertMenu(String id, String roleId, String menuId) {
        menuDao.insertMenu(id,roleId,menuId);
    }

    public List<UserRoleMenu> getRoleByUserId(String roleId) {
        return menuDao.getRoleByUserId(roleId);
    }

    public List<Map<String,Object>> getMenuTree() {
        return menuDao.getMenuTree();
    }

    public List<Menu> menuDisplay() {
        return menuDao.menuDisplay();
    }

    public List<Menu> parentList() {
        return menuDao.parentList();
    }


    public void updateLocalMenu(String id,String name,String url,String icons,String orderNum,String pid) {
        menuDao.updateLocalMenu(id,name,url,icons,orderNum,pid);
    }


    public void creatMenu(String id, String name, String url, String icons, String orderNum, String pid) {
        menuDao.creatMenu(id,name,url,icons,orderNum,pid);
    }

    public void updateMenuState(String id) {
        menuDao.updateMenuState(id);
    }


}
