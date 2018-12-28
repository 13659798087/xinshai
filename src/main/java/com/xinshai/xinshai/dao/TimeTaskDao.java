package com.xinshai.xinshai.dao;

import com.xinshai.xinshai.model.TimeTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TimeTaskDao {


    @Select("SELECT * FROM ( SELECT ROW_NUMBER() OVER(ORDER BY u.createTime DESC) AS num, u.*\n" +
            "from timeTask u where 1=1 )AS t WHERE  t.num BETWEEN #{pageNo} AND #{pageSize} ")
    List<TimeTask> listTimeTask(@Param(value="pageNo") int pageNo,@Param(value="pageSize")int pageSize);

    @Select("select count(1) from timeTask ")
    long getTimeTaskCount();

    @Select("select cronExpressions from timeTask where timeTaskId = #{timetaskId} ")
    String getCorn(String timetaskId);

    @Update("update timeTask set state = #{state} where timeTaskId = #{timeTaskId} ")
    void pcChangeState(@Param(value = "timeTaskId") String timeTaskId,@Param(value = "state") String state);

    @Update("update timeTask set taskDescription = #{taskDescription},cronExpressions=#{cronExpressions} where timeTaskId = #{timeTaskId} ")
    void updateTimeTask(@Param(value = "taskDescription") String taskDescription,
                        @Param(value = "cronExpressions") String cronExpressions,
                        @Param(value = "timeTaskId") String timeTaskId);
}
