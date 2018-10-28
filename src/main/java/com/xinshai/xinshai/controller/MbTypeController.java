package com.xinshai.xinshai.controller;


import com.xinshai.xinshai.model.MbType;
import com.xinshai.xinshai.services.MbTypeServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/mbType")
public class MbTypeController {

    @Resource
    private MbTypeServices mbTypeServices;

    @ResponseBody
    @RequestMapping("/getMbType")
    protected List<MbType> getMbType(){
        List<MbType> listMbType = mbTypeServices.getMbType();
        return listMbType;
    }

    @ResponseBody
    @RequestMapping("/getMbTypeById")
    protected MbType getMbTypeById(String id){
        MbType mbType = mbTypeServices.getMbTypeById(id);
        return mbType;
    }





}
