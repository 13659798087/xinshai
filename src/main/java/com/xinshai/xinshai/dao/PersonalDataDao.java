package com.xinshai.xinshai.dao;

import com.xinshai.xinshai.model.PersonBinding;
import com.xinshai.xinshai.model.ResultPushMsg;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface PersonalDataDao {

    @Insert("INSERT INTO personBinding (id,openId,m_name,bloodCard,bithday,tel,createTime) " +
            " VALUES (#{id},#{openId},#{m_name},#{bloodCard},#{bithday},#{tel},getDate() )")
    void createWeixinMsg(@Param(value = "id") String id, @Param(value = "openId") String openId,
                         @Param(value = "m_name") String m_name,@Param(value = "bloodCard")String bloodCard,
                         @Param(value = "tel") String tel,@Param(value = "bithday") String bithday);

    @Select("SELECT * FROM personBinding WHERE openId=#{openId} ")
    List<PersonBinding> getMsgByOpenid(String openId);

    @Delete("DELETE from personBinding where id = #{id} ")
    void deleteBind(String id);

    @Select("SELECT * FROM resultPushMsg WHERE openId=#{openId} ")
    List<ResultPushMsg> getResultItem(String openId);

}
