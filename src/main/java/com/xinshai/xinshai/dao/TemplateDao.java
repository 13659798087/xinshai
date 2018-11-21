package com.xinshai.xinshai.dao;

import com.xinshai.xinshai.model.Template;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TemplateDao {


    @Select("SELECT * FROM (SELECT *,ROW_NUMBER() OVER(ORDER BY orderNum asc) AS num FROM template " +
            "where meaning like '%${meaning}%')AS t WHERE isDelete = 0 and t.num BETWEEN #{pageNo} AND #{pageSize} ")
    List<Template> templateTable(@Param(value="meaning")String meaning,
                                 @Param(value="pageNo")int pageNo,
                                 @Param(value="pageSize")int pageSize);


    @Select("select count(*) from template where isDelete = 0 and meaning like '%${meaning}%' ")
    long getSignCount(@Param(value="meaning")String meaning,
                      @Param(value="pageNo")int pageNo,
                      @Param(value="pageSize")int pageSize);


    @Insert("INSERT into template (id,templateId,first,keyword1,keyword2,keyword3,keyword4,keyword5,remark,keyCount,meaning,orderNum) " +
    "VALUES (#{id},#{templateId},#{first},#{keyword1},#{keyword2},#{keyword3},#{keyword4},#{keyword5},#{remark},#{keyCount},#{meaning},#{orderNum} )")
    void createCombine(@Param(value="id")String id,@Param(value="templateId")String templateId, @Param(value="first")String first,
                       @Param(value="keyword1")String keyword1,@Param(value="keyword2")String keyword2,
                       @Param(value="keyword3")String keyword3, @Param(value="keyword4")String keyword4,
                       @Param(value="keyword5")String keyword5, @Param(value="remark")String remark,
                       @Param(value="keyCount")String keyCount,
                       @Param(value="meaning") String meaning, @Param(value="orderNum")String orderNum);

    @Update("update template set templateId=#{templateId},first=#{first},keyword1=#{keyword1},keyword2=#{keyword2},keyword3=#{keyword3}," +
    "keyword4=#{keyword4},keyword5=#{keyword5},remark=#{remark},keyCount=#{keyCount},meaning=#{meaning},orderNum=#{orderNum} where id=#{id} ")
    void updateCombine(@Param(value="id")String id,@Param(value="templateId")String templateId, @Param(value="first")String first,
                       @Param(value="keyword1")String keyword1, @Param(value="keyword2")String keyword2,
                       @Param(value="keyword3")String keyword3, @Param(value="keyword4")String keyword4,
                       @Param(value="keyword5")String keyword5, @Param(value="remark")String remark,
                       @Param(value="keyCount")String keyCount,
                       @Param(value="meaning") String meaning, @Param(value="orderNum")String orderNum);

    @Update("update template set isDelete= 1 where id=#{id} ")
    void deleteRow(String id);


    @Select("SELECT * FROM template WHERE id=#{id} ")
    Template getById(String id);

}
