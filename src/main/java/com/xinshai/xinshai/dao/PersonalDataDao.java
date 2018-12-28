package com.xinshai.xinshai.dao;

import com.xinshai.xinshai.entiry.ListResult;
import com.xinshai.xinshai.entiry.ReportMsg;
import com.xinshai.xinshai.entiry.WinxinPerson;
import com.xinshai.xinshai.model.PersonBinding;
import com.xinshai.xinshai.model.ResultPushMsg;
import com.xinshai.xinshai.model.Signpic;
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

    @Select("SELECT * FROM Signpic WHERE sp_name = '宜春市产前筛查中心' ")
    Signpic get1();

    @Insert("INSERT INTO personBinding (id,openId,m_name,bloodCard,bithday,tel,createTime,tes,hospitalNo,m_age," +
            "f_name,tel_2,address,pregnanciesNum,deliverNum,pregnancyWeek,pregnancyDay,sex,weigh) " +
            " VALUES (#{id},#{openId},#{m_name},#{bloodCard},#{bithday},#{tel},getDate(),#{tes},#{hospitalNo},#{m_age}," +
            "#{f_name},#{tel_2},#{address},#{pregnanciesNum},#{deliverNum},#{pregnancyWeek},#{pregnancyDay},#{sex},#{weigh} ) ")
    void createpersonlMsg(@Param(value="id")String id,@Param(value="openId")String openId,
                          @Param(value="m_name")String m_name,@Param(value="bloodCard")String bloodCard,
                          @Param(value="tel")String tel,@Param(value="bithday")String bithday,
                          @Param(value="tes")String tes,@Param(value="hospitalNo")String hospitalNo,
                          @Param(value="m_age")String m_age, @Param(value="f_name")String f_name,
                          @Param(value="tel_2")String tel_2, @Param(value="address")String address,
                          @Param(value="pregnanciesNum")String pregnanciesNum,@Param(value="deliverNum")String deliverNum,
                          @Param(value="pregnancyWeek")String pregnancyWeek,@Param(value="pregnancyDay")String pregnancyDay,
                          @Param(value="sex")String sex,@Param(value="weigh")String weigh);


    @Insert("<script> INSERT INTO personBinding (id,openId,m_name,bloodCard,bithday,tel,createTime,tes,hospitalNo,m_age," +
               " f_name,tel_2,address,pregnanciesNum,deliverNum,pregnancyWeek,pregnancyDay,sex,weigh) VALUES " +
               " <foreach collection='list' item='item' separator=',' > \n" +
               " (#{item.id},#{item.openId},#{item.m_name},#{item.bloodCard},#{item.bithday},#{item.tel},getDate(),#{item.tes}," +
               " #{item.hospitalNo},#{item.m_age},#{item.f_name},#{item.tel_2},#{item.address},#{item.pregnanciesNum}," +
               " #{item.deliverNum},#{item.pregnancyWeek},#{item.pregnancyDay},#{item.sex},#{item.weigh} ) \n" +
               " </foreach> </script> ")
    void insertBatchPersonMsg(@Param(value = "list")List<PersonBinding> list);


    @Select("select patientId,m_name,bloodCard,openId,isPass from personBinding where openId = #{openId} ")
    List<PersonBinding> getPatientList(String openId);

    @Select("EXEC pro_wx_getrptno #{patientId} ") //EXEC 存储过程的名称  参数
    List<WinxinPerson> getPersonReport(String patientId);

    @Select("EXEC pro_wx_findrpt_getpid #{card_no},#{m_name},#{bithday} ")
    String queryPatientId(@Param(value="card_no")String card_no,
                          @Param(value="m_name")String m_name,@Param(value="bithday")String bithday);


}
