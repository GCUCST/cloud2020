package com.cst.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    public  String paymentInfo(Integer id ){
        return "线程池"+Thread.currentThread().getName()
                +"payment_ok_"+id;
    }


    //服务降级，如果执行该任务超时 3秒，保底返回这个方法
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandelr",
            commandProperties = {@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",
               value="5000")})
    public  String paymentTimeout(Integer id ){
//        int a = 10/0;      //超时和运行异常。兜底方案
        try {
            TimeUnit.MILLISECONDS.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池"+Thread.currentThread().getName()
                +"payment_ok_"+id+" 耗时三秒。 ";
    }

    public String paymentInfo_TimeOutHandelr(Integer id){
        return "线程池"+Thread.currentThread().getName()
                +"失败了！"+id+" >_<|||";
    }

    //====服务熔断===========
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback"
    ,commandProperties ={
            @HystrixProperty(name="circuitBreaker.enabled",value = "true"),//开启断路器
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//时间窗口期
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value = "60"),//失败达到多少次跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if(id<0){
            throw new RuntimeException();
        }
        return "paymentCircuitBreaker："+id+"\t"+ UUID.randomUUID().toString();
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        String uuid = IdUtil.simpleUUID();
        return "id不能为负数，请稍后再试。"+uuid;
    }





}
