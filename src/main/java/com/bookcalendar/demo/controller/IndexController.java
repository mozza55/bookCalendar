package com.bookcalendar.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin/index";
    }


    @ResponseBody
    @GetMapping("/test")
    public String test(){
        System.out.println("요청 들어옴");
        return "test 성공";
    }
}
