package com.xinshai.xinshai.dao;

import com.xinshai.xinshai.model.PersonBinding;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface PatientsDao {


    @Select("SELECT * from patients where p_m_name = #{p_m_name} and p_tel1= #{p_tel1} " +
            " and  CONVERT(varchar, p_bithday, 23) = #{p_bithday} ")
    PersonBinding validataPerson(@Param(value="p_m_name")String p_m_name,
                       @Param(value="p_tel1")String p_tel1,
                       @Param(value="p_bithday")String p_bithday);

    //查询没通过的验证且创建时间超过10天内的记录   //未验证通过  //未推送过
    @Select("select * from personBinding where isPass = 2 and msgPush=1 and GETDATE() > DATEADD(day, #{dayCount}, createTime)")
    List<PersonBinding> getTenAndNoPass(Integer dayCount);

    //@Select("EXEC isPass")

    @Update("UPDATE personBinding SET isPass=2 WHERE id=#{id} ")
    int updatePass(String id);

    //查询通过的验证且创建时间超过10天的记录，且将这些记录状态改为2，即验证不通过,且没接收到推送消息，即msgPush=1,
    @Select("select * from personBinding where isPass = 0 and GETDATE()<=DATEADD(day, #{dayCount}, createTime)")
    List<PersonBinding> getTenAndNoPass2(Integer dayCount);

    @Update("UPDATE personBinding SET isPass = 2 WHERE id=#{id} ")
    int updatePass2(String id);


}
