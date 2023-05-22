package com.example.LoadBalancerNew.controller;

import com.example.LoadBalancerNew.model.LoadBalancerModel;
import com.example.LoadBalancerNew.model.Server;
import com.example.LoadBalancerNew.pojo.Request.LoadBalancerRequest;
import com.example.LoadBalancerNew.service.LoadBalancerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/load_balancer")
public class BaseController {

    @Autowired
    LoadBalancerService loadBalancerService;

    @PostMapping("/create")
    ResponseEntity<?> createLoadBalancer(@Validated @RequestBody LoadBalancerRequest loadBalancerRequest, @NonNull String name) {
        LoadBalancerModel loadBalancerModel = loadBalancerService.create(loadBalancerRequest, name);
        return new ResponseEntity<>(loadBalancerModel, HttpStatus.OK);
    }

    @PostMapping("/server/register")
    ResponseEntity<?> registerServer(@NonNull String name, @NonNull @RequestBody Server server) {
        try {
            LoadBalancerModel loadBalancerModel = loadBalancerService.registerServer(name, server);
            return new ResponseEntity<>(loadBalancerModel, HttpStatus.OK);
        } catch (Exception e) {
           return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/server/de-register")
    ResponseEntity<?> deRegisterServer(@NonNull String name, @NonNull @RequestBody Server server) {
        try {
            LoadBalancerModel loadBalancerModel = loadBalancerService.deRegisterServer(name, server);
            return new ResponseEntity<>(loadBalancerModel, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
