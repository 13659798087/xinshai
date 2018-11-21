package com.xinshai.xinshai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/open/messagePage")
public class TimeScheduleController {

    private String view = "messagePage/";

    @RequestMapping("/remind")
    public String remind(){
        return view+"remind";
    }


}
