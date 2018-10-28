package com.xinshai.xinshai.services;


import com.xinshai.xinshai.dao.CoursesDao;
import com.xinshai.xinshai.model.Courses;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CoursesServices {

    @Resource
    private CoursesDao coursesDao;

    public List<Courses> getcourse() {
        return coursesDao.getcourse();
    }
}
