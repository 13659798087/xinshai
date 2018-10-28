package com.xinshai.xinshai.dao;

import com.xinshai.xinshai.model.WeixinMsg;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface PersonalDataDao {

    @Insert("INSERT INTO weixinMsg (id,openId,p_name,s_birthday,p_tel,createTime) " +
                   " VALUES (#{id},#{openId},#{p_name},#{s_birthday},#{p_tel},getDate() )")
    void createWeixinMsg(@Param(value = "id") String id, @Param(value = "openId") String openId,
                         @Param(value = "p_name") String p_name, @Param(value = "p_tel") String p_tel,
                         @Param(value = "s_birthday") Timestamp s_birthday);

    @Select("SELECT * FROM weixinMsg WHERE openId=#{openId} ")
    List<WeixinMsg> getMsgByOpenid(String openId);

    @Delete("DELETE from weixinMsg where id = #{id} ")
    void deleteBind(String id);

}
