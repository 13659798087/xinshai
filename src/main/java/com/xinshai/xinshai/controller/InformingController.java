package com.xinshai.xinshai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/informing")
public class InformingController {


    private String  view ="informing/";

    @RequestMapping("/informing")
    public String informing(){
        return view + "informing";
    }


}
