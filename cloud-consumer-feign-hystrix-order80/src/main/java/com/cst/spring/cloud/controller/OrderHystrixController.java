package com.cst.spring.cloud.controller;

import com.cst.spring.cloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
//@DefaultProperties(defaultFallback = "defaultTimeOutFallbackMethod")
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;


    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_ok(@PathVariable("id") Integer id){
        return paymentHystrixService.paymentInfo_ok(id);
    }

//    @HystrixCommand(fallbackMethod = "paymentTimeOutFallbackMethod",
//            commandProperties = {@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",
//                    value="1500")})

    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
//    @HystrixCommand
    public String paymentInfo_timeout(@PathVariable("id") Integer id)
    {
        return paymentHystrixService.paymentInfo_timeout(id);
    }
    //全局fallback
    public String paymentTimeOutFallbackMethod(@PathVariable("id") Integer id)
    {
        return "我是消费者80，对方支付系统缓慢！";
    }

    public String defaultTimeOutFallbackMethod()
    {
        return "defaultTimeOutFallbackMethod:我是消费者80，对方支付系统缓慢！";
    }





}
