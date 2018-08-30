package com.rosam.product.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {

    @RequestMapping("/getMsg")
    public String getMsg(){
        System.out.println("111");
        return "this is product message 2";
    }
}
