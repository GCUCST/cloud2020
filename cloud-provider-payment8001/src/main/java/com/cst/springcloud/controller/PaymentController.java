package com.cst.springcloud.controller;

import com.cst.springcloud.entities.CommonResult;
import com.cst.springcloud.entities.Payment;
import com.cst.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Resource    //注册的服务发现
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/payment/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        for (String element:services){
            log.info("----元素--"+element);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance element:instances){
            log.info(element.getServiceId()+"\t"+element.getHost()+"\t"+
                    element.getPort()+"\t"+element.getUri());
        }
        return this.discoveryClient;
    }

    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("----加入结果："+result);
        if(result>0){
            return  new CommonResult(200,"成功"+serverPort,result );
        }
        else{
            return  new CommonResult(444,"失败:"+serverPort,null );
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
       System.out.println("进入 :"+id);
        Payment payment = paymentService.getPaymentById(id);
        log.info("----查询："+payment);
        if(payment!=null){
            return  new CommonResult(200,"成功"+serverPort,payment );
        }
        else{
            return  new CommonResult(444,"失败"+serverPort,null );
        }
    }

    @GetMapping("/payment/lb")
    public  String getPaymentLB(){
        return "10000000";
    }

    @GetMapping("/payment/feign/timeout")
    public String paymentFeignTimeout() {
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "1001";
    }


}
