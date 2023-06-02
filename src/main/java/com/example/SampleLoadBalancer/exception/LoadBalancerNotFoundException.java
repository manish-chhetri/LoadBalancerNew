package com.example.SampleLoadBalancer.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LoadBalancerNotFoundException extends Exception {

    @Autowired
    public LoadBalancerNotFoundException(@Value("${exception.loadBalancer.notFound}") String message) {
        super(message);
    }
}
