package com.xinshai.xinshai.dao;

import com.xinshai.xinshai.model.CheckinfoTest;
import com.xinshai.xinshai.model.PersonBinding;
import com.xinshai.xinshai.model.ResultPushMsg;
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
            "<if test='concerns !=null &amp;&amp; concerns !=\"\"'> and o.concerns like '%${concerns}%' </if>\"+\n" +*/
            ")AS t WHERE  t.num BETWEEN #{pageNo} AND #{pageSize} </script> ")
    List<WeixinUserInfo> getUserMessage(@Param(value = "pageNo") int pageNo,
                                        @Param(value = "pageSize") int pageSize,
                                        @Param(value = "userName") String userName,
                                        @Param(value = "concerns") String concerns);



    @Select("<script> SELECT count(1) FROM (\n" +
            "SELECT ROW_NUMBER() OVER(ORDER BY u.attentionTime DESC) AS num, u.*\n" +
            "from weixinUserinfo u where 1=1 \n" +
            /*"<if test='userName !=null &amp;&amp; userName !=\"\"'> and userName like '%${userName}%' </if>\n" +
            "<if test='organizationName !=null &amp;&amp; organizationName !=\"\"'> and o.name like '%${organizationName}%' </if>\n" +*/
            ")AS t  </script> ")
    long getUserCount(@Param(value = "pageNo") int pageNo,
                      @Param(value = "pageSize") int pageSize,
                      @Param(value = "userName") String userName,
                      @Param(value = "concerns") String concerns);


    @Insert("INSERT INTO weixinUserinfo (openid,nickname,sex,language,city,province,country,groupid,attentionTime,remark,createTime) " +
            "VALUES (#{openid},#{nickname},#{sex},#{language},#{city},#{province},#{country},#{groupid},#{attentionTime},#{remark},getDate() )")
    void insertUserOpenid(@Param(value = "openid") String openid, @Param(value = "nickname") String nickname,
                          @Param(value = "sex") String sex, @Param(value = "language") String language,
                          @Param(value = "city") String city, @Param(value = "province") String province,
                          @Param(value = "country") String country, @Param(value = "groupid") String groupid,
                          @Param(value = "attentionTime") Timestamp attentionTime,@Param(value = "remark") String remark);

    @Select("SELECT * FROM checkinfoTest")
    List<CheckinfoTest> getTest();

    @Select("SELECT * FROM personBinding where p_name = #{m_name} and p_tel = #{p_tel}")
    PersonBinding getByApplyinfo(@Param(value = "m_name") String m_name, @Param(value = "p_tel") String p_tel);


    @Delete("update weixinUserinfo set concerns = #{concerns},nickname=#{nickname},language=#{language},city=#{city},province=#{province}," +
            " country=#{country},attentionTime=#{attentionTime},groupid=#{groupid},remark=#{remark},createTime=getDate() where openid=#{openid}")
    void UpdateConcerns(@Param(value = "openid") String openid, @Param(value = "concerns") int concerns,
                        @Param(value = "nickname")String nickname,@Param(value = "language") String language,
                        @Param(value = "city")String city, @Param(value = "province")String province,
                        @Param(value = "country")String country, @Param(value = "groupid")String groupid,
                        @Param(value = "attentionTime")Timestamp attentionTime, @Param(value = "remark")String remark);

    @Delete("update weixinUserinfo set concerns = #{concerns} where openid=#{openid}")
    void UpdateConcerns1(@Param(value = "openid") String openid, @Param(value = "concerns") int concerns);

    @Delete("DELETE FROM personBinding where openid=#{fromUserName}")
    void deleteWeixinMsg(String fromUserName);

    @Delete("DELETE FROM weixinUserinfo ")
    void deletaAll();

    @Update("update weixinUserinfo set remark = #{remark} where openid = #{openid} " )
    void updateLocalRemark(@Param(value = "openid") String openid, @Param(value = "remark") String remark);

    @Update("update weixinUserinfo set tagid_list = #{tagid1} where openid = #{openid} " )
    void updateTag(@Param(value = "openid") String openid, @Param(value = "tagid1") String tagid1);

    @Update("update weixinUserinfo set binding = 0 where openid = #{openId} " )
    void bindMessage(String openId);

    @Select("select count(1) from weixinUserinfo where openid = #{openid} ")
    int getByOpenid(String openid);

                                                // 关注的       未绑定的
    @Select("select * from weixinUserinfo where concerns=0 and binding=1 and DATEDIFF(dd,attentionTime,getdate())>=#{dayCount} and pushCount<#{bindRemind}")
    List<WeixinUserInfo> getRecentlyUser(@Param(value="dayCount")int dayCount,@Param(value="bindRemind")int bindRemind);

    @Insert("INSERT INTO alreadyPushMsg (id,openId,bloodCard,p_m_name,combineName,reportDate,reportType,templateId) " +
            "VALUES ${value} ")
    void pushHistory(String value);

    @Delete("DELETE ResultPushMsg")
    void deleteResult();

    @Update("update weixinUserinfo set pushCount=pushCount+1 where openid in ${successId} ")
    void updatePushCount(@Param(value = "successId") String successId);

    // 关注的       未绑定的
    @Select("select * from weixinUserinfo where concerns=0 and binding=1 and DATEDIFF(dd,attentionTime,getdate())=#{dayCount} ")
    List<WeixinUserInfo> getConfigDay(int dayCount);

    @Insert("<script> insert into weixinUserinfo (openid,nickname,sex,language,city,province," +
            " country,attentionTime,remark,createTime) values  " +
            " <foreach collection='listUserInfo' item='item' separator=',' > " +
            " (#{item.openid},#{item.nickname},#{item.sex},#{item.language},#{item.city}," +
            " #{item.province},#{item.country},#{item.attentionTime},#{item.remark},getDate() )\n" +
            " </foreach> </script> ")
    void insertBatchUser(@Param(value = "listUserInfo")List<WeixinUserInfo> listUserInfo);


}
