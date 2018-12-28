package com.xinshai.xinshai.dao;

import com.xinshai.xinshai.model.OperationLog;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface OperationLogDao {

    @Select(" <script> SELECT * FROM (SELECT *,ROW_NUMBER() OVER(ORDER BY createTime asc) AS num FROM operationLog where 1=1 \n" +
            " <if test='time_1 !=null '> and createTime between #{time_1} and #{time_2} </if>"+
            " <if test='operationType !=null &amp;&amp; operationType !=\"\"'> and operationType like '%${operationType}%' </if>"+
            " <if test='content !=null &amp;&amp; content !=\"\"'> and content like '%${content}%' </if>"+
            ") AS t WHERE  t.num BETWEEN #{pageNo} AND #{pageSize} </script> ")
    List<OperationLog> operationTable(@Param(value = "pageNo") int pageNo, @Param(value = "pageSize") int pageSize,
                                      @Param(value = "time_1") Timestamp time_1, @Param(value = "time_2") Timestamp time_2,
                                      @Param(value="operationType")String operationType,@Param(value="content")String content);

    @Select("<script> SELECT count(*) from operationLog where 1=1 " +
            " <if test='time_1 !=null '> and createTime between #{time_1} and #{time_2} </if>" +
            " <if test='operationType !=null &amp;&amp; operationType !=\"\"'> and operationType like '%${operationType}%' </if> " +
            " <if test='content !=null &amp;&amp; content !=\"\"'> and content like '%${content}%' </if> </script>" )
    long getoperationCount(@Param(value = "pageNo") int pageNo, @Param(value = "pageSize") int pageSize,
                           @Param(value = "time_1") Timestamp time_1, @Param(value = "time_2") Timestamp time_2,
                           @Param(value="operationType")String operationType,@Param(value="content")String content);

    @Insert(" INSERT INTO operationLog (operationId,operationType,content,openid,deviceName,ipAddress,createTime)\n" +
            " VALUES (#{operationId},#{operationType},#{content},#{openid},#{deviceName},#{ipAddress},getDate() ) ")
    void recordLog(@Param(value = "operationId") String operationId, @Param(value = "operationType") String operationType,
                   @Param(value = "content")String content,@Param(value = "openid")String openid,
                   @Param(value = "deviceName")String deviceName,@Param(value = "ipAddress")String ipAddress);

    @Delete("delete from operationLog where operationId = #{id} ")
    void deleteLog(String id);



}
