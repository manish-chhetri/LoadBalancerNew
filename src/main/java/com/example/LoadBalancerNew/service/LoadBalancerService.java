package com.example.LoadBalancerNew.service;

import com.example.LoadBalancerNew.exception.LoadBalancerNotFoundException;
import com.example.LoadBalancerNew.model.LoadBalancerModel;
import com.example.LoadBalancerNew.model.Server;
import com.example.LoadBalancerNew.pojo.Request.LoadBalancerRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class LoadBalancerService {

    Map<String, LoadBalancerModel> loadBalancerModelMap = new HashMap<String, LoadBalancerModel>();


    public LoadBalancerModel create(LoadBalancerRequest loadBalancerRequest, String name) {
        LoadBalancerModel loadBalancerModelObj;
        if (!loadBalancerModelMap.containsKey(name)) {
            loadBalancerModelObj = new LoadBalancerModel(loadBalancerRequest.getServiceTimeoutInMs(),
                    loadBalancerRequest.getHealthCheckIntervalInSeconds(),
                    loadBalancerRequest.getHealthCheckUrl(),
                    loadBalancerRequest.getHealthCheckPort(),
                    loadBalancerRequest.getFailureThresholdTimes()
            );
            loadBalancerModelMap.put(name, loadBalancerModelObj);
        } else {
            loadBalancerModelObj = loadBalancerModelMap.get(name);
        }
        return loadBalancerModelObj;
    }

    public LoadBalancerModel registerServer(String name, Server server) throws LoadBalancerNotFoundException {
        if (!loadBalancerModelMap.containsKey(name)) {
            throw new LoadBalancerNotFoundException("Load balancer not found");
        }

        LoadBalancerModel loadBalancerModelObj = loadBalancerModelMap.get(name);

        loadBalancerModelObj.registerServer(server);

        return loadBalancerModelObj;
    }

    public LoadBalancerModel deRegisterServer(String name, Server server) throws Exception {
        if (!loadBalancerModelMap.containsKey(name)) {
            throw new LoadBalancerNotFoundException("Load balancer not found");
        }

        LoadBalancerModel loadBalancerModelObj = loadBalancerModelMap.get(name);
        loadBalancerModelObj.deRegisterServer(server);
        return loadBalancerModelObj;
    }

    public void assignRandom(String name, int requestID) throws LoadBalancerNotFoundException {
        if (!loadBalancerModelMap.containsKey(name)) {
            throw new LoadBalancerNotFoundException("Load balancer not found");
        }

        LoadBalancerModel loadBalancerModelObj = loadBalancerModelMap.get(name);
        List<Server> serverList = loadBalancerModelObj.getActiveServerList();

        int size = serverList.size();
        Random random = new Random();
        int randomKey = random.ints(0, size-1).findFirst().getAsInt();
        Server server = serverList.get(randomKey);
        server.assignRequest(requestID);
    }

    public void assignRoundRobin(String name, int requestId) throws LoadBalancerNotFoundException {
        if (!loadBalancerModelMap.containsKey(name)) {
            throw new LoadBalancerNotFoundException("Load balancer not found");
        }

        LoadBalancerModel loadBalancerModelObj = loadBalancerModelMap.get(name);
        List<Server> activeServerList = loadBalancerModelObj.getActiveServerList();

        int size = activeServerList.size();

        int serverKey = loadBalancerModelObj.getSerialKey();

        Server server = activeServerList.get(serverKey);
        server.assignRequest(requestId);

        serverKey++;
        if (serverKey >= size) {
            serverKey = 0;
        }

        loadBalancerModelObj.setSerialKey(serverKey);
    }
}
