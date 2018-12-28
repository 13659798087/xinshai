package com.xinshai.xinshai.dao;

import com.xinshai.xinshai.model.Menu;
import com.xinshai.xinshai.model.UserRoleMenu;
import com.xinshai.xinshai.model.WeixinMenu;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface MenuDao {

    @Select("select * from menu")
    List<Menu> getAllMenu();


    @Select("SELECT * FROM (SELECT *,ROW_NUMBER() OVER(ORDER BY orderNum DESC) AS num FROM menu " +
            "where menuName like '%${menuName}%' )AS t WHERE  t.num BETWEEN #{pageNo} AND #{pageSize} ")
    List<Menu> getMenuByNum(@Param(value = "menuName") String menuName,
                            @Param(value = "pageNo") int pageNo,
                            @Param(value = "pageSize") int pageSize);

    @Select("select count(1) from menu where menuName like '%${menuName}%' ")
    long getMenuCount(@Param(value = "menuName") String menuName,
                      @Param(value = "pageNo") int pageNo,
                      @Param(value = "pageSize") int pageSize);

    @Insert("INSERT INTO roleMenu (id,roleId,menuId) VALUES (#{id},#{roleId},#{menuId})")
    void insertMenu(@Param(value = "id") String id,
                    @Param(value = "roleId") String roleId,
                    @Param(value = "menuId") String menuId);

    @Select("SELECT * from role r \n" +
            "LEFT JOIN roleMenu rm on r.roleId=rm.roleId\n" +
            "LEFT JOIN  menu m on rm.menuId=m.menuId where r.roleId=#{roleId} ")
    List<UserRoleMenu> getRoleByUserId(String roleId);

    @Select("select menuId id,menuName name,parentId pId from menu where isDelete = 0 ")
    List<Map<String,Object>> getMenuTree();

    @Select(" SELECT menuId id,menuName name,parentId pid,url,icons,orderNum,isDelete from menu where isDelete=0 order by orderNum ")
    List<Menu> menuDisplay();

    @Select("SELECT menuId,menuName from menu where parentId is null and isDelete = 0 order by orderNum")
    List<Menu> parentList();

    @Update("UPDATE menu SET menuName=#{name},url=#{url},icons=#{icons}," +
            "orderNum=#{orderNum},parentId=#{pid} where menuId=#{id}")
    void updateLocalMenu(@Param(value="id")String id,@Param(value="name") String name,
                         @Param(value="url")String url, @Param(value="icons") String icons,
                         @Param(value="orderNum")String orderNum,@Param(value="pid") String pid);


    @Insert("INSERT into menu (menuId,menuName,url,icons,orderNum,parentId) " +
            " VALUES (#{id},#{name},#{url},#{icons},#{orderNum},#{pid})")
    void creatMenu(@Param(value="id")String id,@Param(value="name")String name,
                   @Param(value="url")String url,@Param(value="icons")String icons,
                   @Param(value="orderNum")String orderNum,@Param(value="pid")String pid);

    @Update("UPDATE menu SET isDelete=1 where menuId = #{id}")
    void updateMenuState(String id);


}
