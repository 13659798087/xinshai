package com.xinshai.xinshai.dao;

import com.xinshai.xinshai.model.Courses;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CoursesDao {
    @Select("select * from courses")
    List<Courses> getcourse();
}
