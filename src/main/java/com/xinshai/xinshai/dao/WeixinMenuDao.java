package com.xinshai.xinshai.dao;

import com.xinshai.xinshai.model.WeixinMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WeixinMenuDao {


    @Select("SELECT id,name,parentId pid,url,menukey,type from weixinMenu order by orderNum ")
    List<WeixinMenu> getWeixinMenu();


}
