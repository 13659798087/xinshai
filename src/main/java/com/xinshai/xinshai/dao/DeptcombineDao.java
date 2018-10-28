package com.xinshai.xinshai.dao;

import com.xinshai.xinshai.model.Combine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DeptcombineDao {
    @Select("select * from combine ")
    List<Combine> getDeptCombine(Integer dcdId);
}
