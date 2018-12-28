package com.xinshai.xinshai.dao;

import com.xinshai.xinshai.entiry.CheckInfoPushMsg;
import com.xinshai.xinshai.entiry.ListResult;
import com.xinshai.xinshai.entiry.ReportMsg;
import com.xinshai.xinshai.entiry.WaitPushMsg;
import com.xinshai.xinshai.model.ResultPushMsg;
import com.xinshai.xinshai.model.WeixinUserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ResultPushMsgDao {



    @Insert("INSERT INTO ResultPushMsg (id,openId,patientId,tid,first,keyword1,keyword2,keyword3,keyword4,keyword5," +
            "createTime,flag,type) VALUES ${value} ")
    void insertBindRemind(String addInsert);

    @Insert("INSERT INTO pushHistory (id,openId,patientId,tid,first,keyword1,keyword2,keyword3,keyword4,keyword5," +
            "createTime,flag,type) VALUES ${value} ")
    void pushHistory(String addInsert);

    @Update("update ResultPushMsg set success=2,flag = flag+1 where id in ${wrongId} ")
    void pushFail(@Param(value = "wrongId") String wrongId);

    @Delete("delete from ResultPushMsg where id in ${successId} ")
    void deleteSuccessId(@Param(value = "successId")String successId);

    /*@Insert("<script> insert into wxinfo (openid,nickname) values  " +
            "    <foreach collection='result' item='item' separator=',' > " +
            "        (#{item.openid},#{item.nickname})\n" +
            "    </foreach> </script>")
    Boolean insertBatchCode(@Param(value = "result") List<WeixinUserInfo> result);*/

   /* @Insert("INSERT INTO ResultPushMsg (id,openId,patientId,tid,first,keyword1,keyword2,keyword3,keyword4,keyword5," +
            "createTime,flag,type) VALUES ${value} ")
    void insertBindRemind(String addInsert);*/

    @Insert("<script> insert into ResultPushMsg (id,openId,tid,first,keyword1,keyword2,keyword3,keyword4,keyword5,remark," +
            " createTime,flag,type) values  " +
            " <foreach collection='result' item='item' separator=',' > " +
            " (#{item.id},#{item.openid},#{item.templateId},#{item.first},#{item.keyword1},#{item.keyword2}," +
            " #{item.keyword3},#{item.keyword4},#{item.keyword5},#{item.remark},getDate(),#{item.flag},#{item.type})\n" +
            " </foreach> </script>")
    Boolean insertBatchCode(@Param(value = "result") List<WeixinUserInfo> result);

    @Insert("<script> INSERT INTO pushHistory (id,openId,patientId,tid,first,keyword1,keyword2,keyword3,keyword4,keyword5,remark," +
            " createTime,flag,type,success) VALUES " +
            " <foreach collection='result2' item='item' separator=',' > \n" +
            " (#{item.id},#{item.openId},#{item.patientId},#{item.tid},#{item.first},#{item.keyword1},#{item.keyword2}, \n" +
            " #{item.keyword3},#{item.keyword4},#{item.keyword5},#{item.remark},getDate(),#{item.flag},#{item.type},#{i} ) \n" +
            " </foreach> </script> ")
    void insertBatchHistory(@Param(value = "result2")List<ListResult> result2,@Param(value = "i")int i);

    @Select("select c.c_id,c.c_combine_code,pb.m_name,pb.bloodCard,(select h_name from hospital) authorHospital " +
            "from checkinfo c LEFT JOIN personBinding pb on c.c_p_id = pb.patientId  where openId = #{openId} ")
    List<ReportMsg> getReportByOpenId(@Param(value="openId")String openId);

    /*@Select("select r.id,r.openId,r.tid,r.type,r.createTime,r.flag,r.type,r.keyword1,r.keyword2,\n" +
            "r.keyword3,r.keyword4,r.keyword5,t.templateId,t.[first],t.remark,t.keyCount,\n" +
            "pb.isPass,pb.bloodCard,pb.m_name,c.c_id,c.c_combine_code,(select h_name from hospital) authorHospital\n" +
            "from ResultPushMsg r\n" +
            "LEFT JOIN patients p ON r.patientId=p.p_id\n" +
            "LEFT JOIN template t on r.tid=t.id\n" +
            "LEFT JOIN personBinding pb on (pb.patientId = r.patientId or pb.openId=r.openId)\n" +
            "LEFT JOIN checkinfo c on c.c_p_id=r.patientId ORDER BY r.createTime ")*/

   /* @Select("select r.id,r.openId,r.patientId,r.tid,r.type,r.[first],r.keyword1,r.keyword2,r.keyword3,r.keyword4,r.keyword5,r.flag,\n" +
            "r.success,t.templateId,p.m_name,c.c_id,c.c_combine_code,(select h_name from hospital) authorHospital from ResultPushMsg r\n" +
            "LEFT JOIN personBinding p on r.openId = p.openId\n" +
            "LEFT JOIN template t on r.tid = t.id\n" +
            "LEFT JOIN checkinfo c on c.c_p_id=r.patientId ORDER BY r.createTime; ")*/
    @Select("select r.*,t.templateId from ResultPushMsg r LEFT JOIN template t on t.id = r.tid")
    List<ListResult> getPushResult();

    @Update("update ResultPushMsg set flag=flag+1 where id in ${successId} ")
    void updateFlag(String successId);

    @Select("select * from ResultPushMsg where success = 0 ")
    List<ListResult> get1();

    @Select("SELECT c_p_id,bgtype = case when c_type = 1 then '筛查' else '召复' end,\n" +
            "bgname = case when c_combine = 'ct' then '传统项目'\n" +
            "when  c_combine = 'cl' then '串联质谱'\n" +
            "when  c_combine = 'dp' then '地贫'\n" +
            "when  c_combine = 'el' then '耳聋'\n" +
            "else c_combine end,\n" +
            "c_sid,c_combine\n" +
            "from checkinfo where c_p_id in ${patientIdList}\n" +
            "order by c_id  ")
    List<CheckInfoPushMsg> getCheckinfoPatientId(@Param(value = "patientIdList") String patientIdList);

    @Select(" <script> SELECT * FROM (SELECT *,ROW_NUMBER() OVER(ORDER BY createTime desc) AS num FROM ResultPushMsg where 1=1 \n" +
            " <if test='p_name !=null &amp;&amp; p_name !=\"\"'> and p_name like '%${p_name}%' </if>"+
            " <if test='first !=null &amp;&amp; first !=\"\"'> and first like '%${first}%' </if>"+
            " <if test='type !=null &amp;&amp; type !=\"\"'> and type = #{type} </if>"+
            ") AS t WHERE  t.num BETWEEN #{pageNo} AND #{pageSize} </script> ")
    List<WaitPushMsg> waitSendMsg(@Param(value = "pageNo") int pageNo, @Param(value = "pageSize") int pageSize,
        @Param(value = "p_name")String p_name,@Param(value = "first")String first,@Param(value = "type")String type);

    @Select("<script> SELECT count(*) from ResultPushMsg where 1=1 " +
            " <if test='p_name !=null &amp;&amp; p_name !=\"\"'> and p_name like '%${p_name}%' </if>"+
            " <if test='first !=null &amp;&amp; first !=\"\"'> and first like '%${first}%' </if> " +
            " <if test='type !=null &amp;&amp; type !=\"\"'> and type = #{type} </if> </script>" )
    long waitSendMsgCount(@Param(value ="p_name")String p_name,@Param(value="first")String first,@Param(value="type")String type);

    @Delete("delete from ResultPushMsg where id = #{id} ")
    void deleteSendMsg(String id);

    @Update("update ResultPushMsg set openId=#{openId},patientId=#{patientId},tid=#{tid},first=#{first},keyword1=#{keyword1},keyword2=#{keyword2}," +
            "keyword3=#{keyword3},keyword4=#{keyword4},keyword5=#{keyword5},remark=#{remark},createTime=getDate() where id=#{id} ")
    void updateMsg(@Param(value = "id") String id,@Param(value = "openId") String openId,@Param(value = "patientId") String patientId,
                   @Param(value = "tid") String tid,@Param(value = "first") String first,@Param(value = "keyword1") String keyword1,
                   @Param(value = "keyword2")String keyword2,@Param(value = "keyword3")String keyword3,
                   @Param(value = "keyword4")String keyword4,@Param(value = "keyword5")String keyword5,
                   @Param(value = "remark")String remark);


    @Insert("INSERT INTO ResultPushMsg (id,openId,patientId,tid,createTime,first,keyword1,keyword2,keyword3,keyword4,keyword5,remark) VALUES " +
         " (#{id},#{openId},#{patientId},#{tid},getdate(),#{first},#{keyword1},#{keyword2},#{keyword3},#{keyword4},#{keyword5},#{remark} )")
    void creatMsg(@Param(value = "id") String id,@Param(value = "openId") String openId,@Param(value = "patientId") String patientId,
                  @Param(value = "tid") String tid,@Param(value = "first") String first,@Param(value = "keyword1") String keyword1,
                  @Param(value = "keyword2")String keyword2,@Param(value = "keyword3")String keyword3,
                  @Param(value = "keyword4")String keyword4,@Param(value = "keyword5")String keyword5,@Param(value = "remark")String remark);


    //select r.*,t.templateId from ResultPushMsg r LEFT JOIN template t on t.id = r.tid
    @Select("select r.*,t.templateId from ResultPushMsg r LEFT JOIN template t on t.id = r.tid where r.id in ${listId} ")
    List<ListResult> getListId(@Param(value="listId") String listId);

    //@Select("EXEC pro_wx_getrptno")



}
