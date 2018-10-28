package com.xinshai.xinshai.dao;

import com.xinshai.xinshai.model.Tag;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TagDao {

    @Delete("DELETE FROM tag ")
    void deleteAllTag();

    @Insert("INSERT INTO tag (id,name,count) VALUES (#{id},#{name},#{count} )")
    void insertTag(@Param(value = "id") String id, @Param(value = "name") String name, @Param(value = "count") int count);

    @Select("SELECT name FROM tag where id = #{id} ")
    String getName(String id);

    @Select("SELECT * FROM (SELECT *,ROW_NUMBER() OVER(ORDER BY count) AS num FROM tag " +
            "where name like '%${name}%')AS t WHERE  t.num BETWEEN #{pageNo} AND #{pageSize} ")
    List<Tag> getTagMessage(@Param(value = "pageNo") int pageNo, @Param(value = "pageSize") int pageSize, @Param(value = "name") String name);

    @Select("SELECT count(1) FROM (SELECT *,ROW_NUMBER() OVER(ORDER BY count) AS num FROM tag " +
            "where name like '%${name}%')AS t WHERE  t.num BETWEEN #{pageNo} AND #{pageSize} ")
    long getTagCount(@Param(value = "pageNo") int pageNo, @Param(value = "pageSize") int pageSize, @Param(value = "name") String name);

    @Select("SELECT count(1) FROM tag where name = #{name} ")
    int validateTag(String name);

    @Insert("INSERT INTO tag (id,name) VALUES (#{id},#{name})")
    void creaTag(@Param(value = "id") String id, @Param(value = "name") String name);


    @Update("UPDATE tag SET name=#{name} where id=#{id} ")
    void updateTag(@Param(value = "id") String id, @Param(value = "name") String name);

    @Delete("DELETE FROM tag where id= #{id} ")
    void deleteTag(String id);

    @Select("SELECT * FROM tag where id = #{id} ")
    Tag getTagById(String id);

    @Select("SELECT * FROM tag")
    List<Tag> getAllTag();



}
