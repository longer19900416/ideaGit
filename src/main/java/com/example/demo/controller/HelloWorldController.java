package com.example.demo.controller;

import com.example.demo.service.DemoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @Autowired
    private DemoInterface demoService;
    @RequestMapping("/hello")
    public String hello(){
        return demoService.getHellowWordMsg("this is test program");
    }
}
