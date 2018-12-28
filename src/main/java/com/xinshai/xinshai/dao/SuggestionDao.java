package com.xinshai.xinshai.dao;


import com.xinshai.xinshai.model.Suggestion;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Mapper
public interface SuggestionDao {


    @Insert("insert into suggestion (id,openid,name,phone,mail,suggestion,createTime) values " +
            "(#{id},#{openid},#{name},#{phone},#{mail},#{suggestion},getDate() ) ")
    void wxsubmitSuggestion(@Param(value = "id") String id, @Param(value = "openid")String openid,
                            @Param(value = "name")String name,@Param(value = "phone")String phone,
                            @Param(value = "mail") String mail,@Param(value = "suggestion") String suggestion);


    @Select("select * from suggestion where openid = #{openid} order by createTime desc ")
    List<Map> wxgetSuggestion(String openid);

    @Delete("DELETE FROM suggestion where id = #{id} ")
    void deleteSug(String id);

    @Select("<script> SELECT * FROM (SELECT *,ROW_NUMBER() OVER(ORDER BY createTime desc) AS num FROM suggestion where 1=1 " +
            " <if test='time1 !=null '> and createTime between #{time1} and #{time2} </if>"+
            ") " +
            " AS t WHERE  t.num BETWEEN #{pageNo} AND #{pageSize} </script> ")
    List<Suggestion> getSuggestionList(@Param(value = "time1") Timestamp time1,@Param(value = "time2") Timestamp time2,
                                       @Param(value = "pageNo") int pageNo, @Param(value = "pageSize") int pageSize);

    @Select("<script> SELECT count(*) from suggestion where 1=1 " +
            " <if test='time1 !=null '> and createTime between #{time1} and #{time2} </if>"+
            " </script>" )
    long getSuggestionCount(@Param(value = "time1") Timestamp time1,@Param(value = "time2") Timestamp time2);
}
