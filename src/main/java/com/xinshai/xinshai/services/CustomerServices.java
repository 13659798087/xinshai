package com.xinshai.xinshai.services;

import com.xinshai.xinshai.dao.CustomerDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CustomerServices {

    @Resource
    private CustomerDao customerDao;

}
