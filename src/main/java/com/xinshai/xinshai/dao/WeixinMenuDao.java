package com.xinshai.xinshai.dao;

import com.xinshai.xinshai.model.WeixinMenu;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WeixinMenuDao {


    @Select("SELECT id,name,parentId pid,url,menukey,type,orderNum,isDelete,mainName from weixinMenu where isDelete=0 order by orderNum ")
    List<WeixinMenu> getWeixinMenu();

    @Update("UPDATE weixinMenu SET name=#{name},type=#{type},url=#{url},menukey=#{menukey}," +
            "orderNum=#{orderNum},parentId=#{pid} where id=#{id}")
    void updateLocalMenu(@Param(value="id")String id,@Param(value="name")String name,
                         @Param(value="type")String type,@Param(value="url")String url,
                         @Param(value="menukey")String menukey,@Param(value="orderNum")String orderNum,
                         @Param(value="pid") String pid);

    @Insert("INSERT into weixinMenu (id,name,type,url,menukey,orderNum,parentId) " +
            " VALUES (#{id},#{name},#{type},#{url},#{menukey},#{orderNum},#{pid})")
    void creatMenu(@Param(value="id")String id,@Param(value="name")String name,
                   @Param(value="type")String type,@Param(value="url")String url,
                   @Param(value="menukey")String menukey,@Param(value="orderNum")String orderNum,
                   @Param(value="pid")String pid);

    @Select("SELECT id,name,parentId pid,url,menukey,type,orderNum from weixinMenu where parentId is null order by orderNum")
    List<WeixinMenu> parentList();

    @Update("UPDATE weixinMenu SET isDelete=1 where id=#{id}")
    void updateMenuState(String id);


}
