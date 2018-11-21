package com.xinshai.xinshai.dao;

import com.xinshai.xinshai.model.Config;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface ConfigDao {


    @Select("SELECT * FROM (SELECT *,ROW_NUMBER() OVER(ORDER BY orderNum DESC) AS num FROM config " +
            "where cf_code like '%${cf_code}%')AS t WHERE  t.num BETWEEN #{pageNo} AND #{pageSize} ")
    List<Config> configTable(@Param(value = "cf_code") String cf_code,
                             @Param(value = "pageNo") int pageNo,
                             @Param(value = "pageSize") int pageSize);

    @Select("SELECT count(1) FROM  config where cf_code like '%${cf_code}%' ")
    long getConfigCount(@Param(value = "cf_code") String cf_code,
                        @Param(value = "pageNo") int pageNo,
                        @Param(value = "pageSize") int pageSize);

    @Select("select count(*) from config where cf_code=#{cf_code} ")
    int validateName(String cf_code);

  /*  @Insert("INSERT into applyinfocombine (id,a_id,a_combine_code,a_combine_name)" +
            " VALUES (#{id},#{a_id},#{c_dode},#{c_name} )")*/
    @Insert("insert into config (cf_code,cf_flag,cf_val,cf_explain,orderNum) values (#{cf_code},#{cf_flag},#{cf_val},#{cf_explain},#{orderNum} )")
    void createConfig(@Param(value = "cf_code") String cf_code,
                      @Param(value = "cf_flag") Integer cf_flag,
                      @Param(value = "cf_val") String cf_val,
                      @Param(value = "cf_explain") String cf_explain,
                      @Param(value = "orderNum")String orderNum);

    @Update("update config set cf_flag=#{cf_flag},cf_val=#{cf_val},cf_explain=#{cf_explain},orderNum=#{orderNum} where cf_code=#{cf_code} ")
    void updateConfig(@Param(value = "cf_code") String cf_code,
                      @Param(value = "cf_flag") Integer cf_flag,
                      @Param(value = "cf_val") String cf_val,
                      @Param(value = "cf_explain") String cf_explain,
                      @Param(value = "orderNum")String orderNum);

    @Delete("delete from config where cf_code = #{cf_code} ")
    void deleteRow(String cf_code);


    @Select("select * from config ")
    List<Config> getAllConfig();



}
