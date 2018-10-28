package com.xinshai.xinshai.dao;

import com.xinshai.xinshai.model.CheckinfoTest;
import com.xinshai.xinshai.model.WeixinMsg;
import com.xinshai.xinshai.model.WeixinUserInfo;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface WeixinUserInfoDao {


    @Select("<script> SELECT * FROM ( \n" +
            "SELECT ROW_NUMBER() OVER(ORDER BY u.attentionTime DESC) AS num, u.*\n" +
            "from weixinUserinfo u where 1=1\n" +
            /*"<if test='userName !=null &amp;&amp; userName !=\"\"'> and userName like '%${userName}%' </if>\"+\n" +
            "<if test='organizationName !=null &amp;&amp; organizationName !=\"\"'> and o.name like '%${organizationName}%' </if>\"+\n" +*/
            ")AS t WHERE  t.num BETWEEN #{pageNo} AND #{pageSize} </script> ")
    List<WeixinUserInfo> getUserMessage(@Param(value = "pageNo") int pageNo,
                                        @Param(value = "pageSize") int pageSize,
                                        @Param(value = "userName") String userName,
                                        @Param(value = "organizationName") String organizationName);



    @Select("<script> SELECT count(1) FROM (\n" +
            "SELECT ROW_NUMBER() OVER(ORDER BY u.attentionTime DESC) AS num, u.*\n" +
            "from weixinUserinfo u where 1=1 \n" +
            /*"<if test='userName !=null &amp;&amp; userName !=\"\"'> and userName like '%${userName}%' </if>\n" +
            "<if test='organizationName !=null &amp;&amp; organizationName !=\"\"'> and o.name like '%${organizationName}%' </if>\n" +*/
            ")AS t  </script> ")
    long getUserCount(@Param(value = "pageNo") int pageNo,
                      @Param(value = "pageSize") int pageSize,
                      @Param(value = "userName") String userName,
                      @Param(value = "organizationName") String organizationName);


    @Insert("INSERT INTO weixinUserinfo (openid,nickname,sex,language,city,province,country,groupid,attentionTime,tagid_list,remark) " +
            "VALUES (#{openid},#{nickname},#{sex},#{language},#{city},#{province},#{country},#{groupid},#{attentionTime},#{tagid_list},#{remark})")
    void insertUserOpenid(@Param(value = "openid") String openid, @Param(value = "nickname") String nickname,
                          @Param(value = "sex") String sex, @Param(value = "language") String language,
                          @Param(value = "city") String city, @Param(value = "province") String province,
                          @Param(value = "country") String country, @Param(value = "groupid") String groupid,
                          @Param(value = "attentionTime") Timestamp attentionTime,
                          @Param(value = "tagid_list") String tagid_list, @Param(value = "remark") String remark);

    @Select("SELECT * FROM checkinfoTest")
    List<CheckinfoTest> getTest();

    @Select("SELECT * FROM weixinMsg where p_name = #{m_name} and p_tel = #{p_tel}")
    WeixinMsg getByApplyinfo(@Param(value = "m_name") String m_name, @Param(value = "p_tel") String p_tel);

    @Select("SELECT * FROM weixinMsg")
    List<WeixinMsg> getAllMsg();

    @Delete("DELETE FROM weixinUserinfo where openid=#{fromUserName}")
    void deleteUserInfo(String fromUserName);

    @Delete("DELETE FROM weixinMsg where openid=#{fromUserName}")
    void deleteWeixinMsg(String fromUserName);

    @Delete("DELETE FROM weixinUserinfo ")
    void deletaAll();

    @Update("update weixinUserinfo set remark = #{remark} where openid = #{openid} " )
    void updateLocalRemark(@Param(value = "openid") String openid, @Param(value = "remark") String remark);

    @Update("update weixinUserinfo set tagid_list = #{tagid1} where openid = #{openid} " )
    void updateTag(@Param(value = "openid") String openid, @Param(value = "tagid1") String tagid1);

}
