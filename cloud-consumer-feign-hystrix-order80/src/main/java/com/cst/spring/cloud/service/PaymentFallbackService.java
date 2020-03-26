package com.cst.spring.cloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements  PaymentHystrixService {
    @Override
    public String paymentInfo_ok(Integer id) {
        return "-------PaymentFallbackService----fail------";
    }

    @Override
    public String paymentInfo_timeout(Integer id) {
        return "------paymentInfo_timeout---fail------------";
    }
}
