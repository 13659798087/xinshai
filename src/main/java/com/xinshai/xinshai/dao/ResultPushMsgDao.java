package com.xinshai.xinshai.dao;

import com.xinshai.xinshai.entiry.ListResult;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ResultPushMsgDao {

    @Select("select r.openId,r.tid,r.type,r.createTime,t.templateId,t.[first],t.keyword1,t.keyword2,\n" +
            "t.keyword3,t.keyword4,t.keyword5,t.remark,t.keyCount,\n" +
            "p.isPass,p.bloodCard,p.m_name from ResultPushMsg r \n" +
            "LEFT JOIN template t on r.tid=t.id\n" +
            "LEFT JOIN personBinding p on (p.patientId = r.patientId or p.openId=r.openId)\n" +
            "where r.flag<(select dayCount from messagePush where id = #{dayConutId} ) ORDER BY r.createTime")
    List<ListResult> getPushResult(Integer pushCountId);


    @Insert("insert into ResultPushMsg(id,openId,tid,createTime,type) values" +
            " (#{id},#{openId},#{template1},getDate(),#{i}) ")
    void insertBindRemind(@Param(value="id")String id,@Param(value="openId")String openId,
                          @Param(value="template1")String template1,@Param(value="i")int i);


}
