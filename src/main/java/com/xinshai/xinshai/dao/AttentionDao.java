package com.xinshai.xinshai.dao;

import com.xinshai.xinshai.model.AttentionReply;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AttentionDao {

    @Select("select * from attentionReply order by orderNum")
    List<AttentionReply> getAttentionReply();

    @Select("select count(1) from attentionReply")
    long getReplyCount();

    @Insert("INSERT INTO attentionReply (id,content,orderNum) values (#{id},#{content},#{orderNum}) ")
    void createReply(@Param(value="id")String id,@Param(value="content")String content,@Param(value="orderNum")String orderNum);

    @Update("UPDATE attentionReply set content=#{content},orderNum=#{orderNum} where id=#{id}")
    void updateReply(@Param(value="id")String id,@Param(value="content")String content,@Param(value="orderNum")String orderNum);

    @Delete("DELETE FROM attentionReply WHERE id=#{id} ")
    void deleteReply(String id);


}
