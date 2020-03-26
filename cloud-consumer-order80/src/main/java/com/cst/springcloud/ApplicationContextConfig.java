package com.cst.springcloud;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
//  <bean id="" class=""/>
@Configuration
public class ApplicationContextConfig {
    @Bean
//    @LoadBalanced  //开启负载均衡，默认轮询
    public RestTemplate getTemplate(){
        return new RestTemplate();
    }
}
