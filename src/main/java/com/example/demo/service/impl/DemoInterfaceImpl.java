package com.example.demo.service.impl;

import com.example.demo.service.DemoInterface;
import org.springframework.stereotype.Service;

@Service
public class DemoInterfaceImpl implements DemoInterface {

    public String getHellowWordMsg(String msg) {
        return "hello world , "+msg;
    }
}
