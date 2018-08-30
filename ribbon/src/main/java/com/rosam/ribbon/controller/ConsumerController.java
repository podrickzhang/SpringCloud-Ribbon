package com.rosam.ribbon.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("/getProductMsg")
    public String getProductMsg(){
        //第一种方式(直接使用restTemplate,url写死)
     /*   RestTemplate restTemplate=new RestTemplate();
        String response = restTemplate.getForObject("http://localhost:8081/getMsg",String.class);*/

        //第二种方式（利用loadBalancerClient通过应用名获取url，然后再使用restTemplate发起请求）
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance serviceInstance = loadBalancerClient.choose("PRODUCT");
        String url = String.format("http://%s:%s",serviceInstance.getHost(),serviceInstance.getPort())+"/getMsg";
        String response = restTemplate.getForObject(url,String.class);

        //第三种方式，直接使用注解的方式
     //   String response = restTemplate.getForObject("http://PRODUCT/getMsg",String.class);
        log.info("response={}",response);
        return response;
    }
}
