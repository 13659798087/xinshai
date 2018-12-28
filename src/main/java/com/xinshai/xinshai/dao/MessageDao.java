package com.xinshai.xinshai.dao;

import com.xinshai.xinshai.model.Hospital;
import com.xinshai.xinshai.model.MessagePush;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface MessageDao {

    @Select("<script> SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY u.createTime DESC) AS num, u.* from messagePush u where 1=1 " +
            " <if test='meaning !=null &amp;&amp; meaning !=\"\"'> and meaning like '%${meaning}%' </if>"+
            " )AS t WHERE  t.num BETWEEN #{pageNo} AND #{pageSize} </script> ")
    List<MessagePush> getPushConfig(@Param(value="pageNo")int pageNo,@Param(value="pageSize")int pageSize,@Param(value="meaning")String meaning);

    @Select("<script> SELECT count(1) FROM messagePush u  where 1=1 " +
            "<if test='meaning !=null &amp;&amp; meaning !=\"\"'> and meaning like '%${meaning}%' </if> </script> ")
    long getPushConfigCount(String meaning);


    @Update("update messagePush set meaning=#{meaning},dayCount=#{dayCount},updateTime=getDate() where id=#{id} ")
    void updatePush(@Param(value="id")String id,@Param(value="meaning")String meaning,@Param(value="dayCount")String dayCount);

    @Delete("DELETE FROM messagePush WHERE id = #{id} ")
    void deletePush(String id);

    @Insert("insert into messagePush (id,meaning,dayCount,createTime) values (#{id},#{meaning},#{dayCount},getDate() ) ")
    void createPush(@Param(value="id")String id,@Param(value="meaning")String meaning,@Param(value="dayCount")String dayCount);

    @Select("select dayCount from messagePush where id = #{dayConutId} ")
    int getDayCount(int dayConutId);

    @Select("select * from hospital ")
    Hospital select1();

}
