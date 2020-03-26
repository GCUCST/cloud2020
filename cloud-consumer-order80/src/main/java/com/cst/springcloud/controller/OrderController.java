package com.cst.springcloud.controller;

import com.cst.springcloud.entities.CommonResult;
import com.cst.springcloud.entities.Payment;
import com.cst.springcloud.lb.LoaderBlancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {

    //微服务名称
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private LoaderBlancer loaderBlancer;
    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/consumer/payment/lb")
    public String getPaymentLB(){
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if(instances==null||instances.size()<0)
        {
            return null;
        }
        ServiceInstance serviceInstance = loaderBlancer.instance(instances);
        URI uri = serviceInstance.getUri();
        return restTemplate.getForObject(uri+"/payment/lb",String.class);


    }


    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment)
    {
        //  Object  表示返回JSON
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class);
    }
    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id")Long id){
       System.out.println("进入。。");
        return   restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id, CommonResult.class);
    }

    //getForEntity 更加详细
    @GetMapping("/consumer/payment/getforentity/{id}")
    public CommonResult<Payment> getPayment2(@PathVariable("id")Long id){
        System.out.println("进入。。");
         ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
        if(entity.getStatusCode().is2xxSuccessful()){
            System.err.println(entity.getHeaders());
            return entity.getBody();
        }
        else {
            return new CommonResult<>(444,"操作失败");
        }
    }

}
