package com.xinshai.xinshai.dao;

import com.xinshai.xinshai.model.DynamicConfig;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DynamicConfigDao {

    @Select("SELECT * FROM (SELECT *,ROW_NUMBER() OVER(ORDER BY createTime DESC) AS num FROM dynamicConfig " +
            " ) AS t WHERE  t.num BETWEEN #{pageNo} AND #{pageSize} ")
    List<DynamicConfig> getDynamic(@Param(value = "pageNo") int pageNo, @Param(value = "pageSize") int pageSize);

    @Select("select count(*) from dynamicConfig")
    long getDynamicCount(int pageNo, int pageSize);

    @Update("UPDATE dynamicConfig set page=#{page},pageContent=#{pageContent},image=#{bytes},updateTime=getDate() where id=#{id} ")
    void updateDynamic(@Param(value = "id")String id,@Param(value = "page")String page,
                       @Param(value = "pageContent")String pageContent,@Param(value = "bytes")byte[] bytes);

    @Insert("insert into dynamicConfig (id,page,pageContent,image,createTime) values (#{id},#{page},#{pageContent},#{bytes},getDate() )")
    void createDynamic(@Param(value = "id")String id,@Param(value = "page")String page,
                       @Param(value = "pageContent")String pageContent,@Param(value = "bytes")byte[] bytes);

    @Select("select * from dynamicConfig where id=#{id} ")
    DynamicConfig Pictureshows(String id);

    @Delete("DELETE FROM dynamicConfig where id=#{id}")
    void deleteImage(String id);

    @Select("select pageContent from dynamicConfig where id=#{id} ")
    String getMyMessage(String id);

    @Update("UPDATE dynamicConfig set page=#{page},pageContent=#{pageContent},updateTime=getDate() where id=#{id} ")
    void updateDynamic1(@Param(value="id")String id,@Param(value="page")String page,@Param(value = "pageContent")String pageContent);


}
