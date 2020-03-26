package com.cst.springcloud.controller;

import com.cst.springcloud.entities.CommonResult;
import com.cst.springcloud.entities.Payment;
import com.cst.springcloud.service.PaymentFeignService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderFeignController {
    @Resource
    private PaymentFeignService paymentFeignService;
    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id")Long id){
        System.out.println(1111);
        return  paymentFeignService.getPaymentById(id);
    }

    @GetMapping("/consumer/feign/timeout")
    public String paymentFeignTimeout(){
        return paymentFeignService.paymentFeignTimeout();
    }

}
