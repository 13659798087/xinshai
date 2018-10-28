package com.xinshai.xinshai.dao;

import com.xinshai.xinshai.model.MbType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MbTypeDao {


    @Select("SELECT * from mbType ")
    List<MbType> getMbType();

    @Select("SELECT * from mbType where id = #{id}")
    MbType getMbTypeById(String id);



}
