package com.example.SampleLoadBalancer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
public class LoadBalancerModel {

    @JsonProperty("backend_servers")
    List<Server> serverList;

    @JsonProperty("active_servers")
    List<Server> activeServerList;

    @NonNull
    @JsonProperty("service_timeout_in_ms")
    Integer serviceTimeoutInMs;
    @NonNull
    @JsonProperty("healthcheck_interval_in_seconds")
    Integer healthCheckIntervalInSeconds;
    @NonNull
    @JsonProperty("healthcheck_url")
    String healthCheckUrl;
    @NonNull
    @JsonProperty("healthcheck_port")
    Integer healthCheckPort;
    @NonNull
    @JsonProperty("failure_threshold_times")
    Integer failureThresholdTimes;

    int serialKey;


    public LoadBalancerModel(@NonNull Integer serviceTimeoutInMs, @NonNull Integer healthCheckIntervalInSeconds, @NonNull String healthCheckUrl, @NonNull Integer healthCheckPort, @NonNull Integer failureThresholdTimes) {
        this.serviceTimeoutInMs = serviceTimeoutInMs;
        this.healthCheckIntervalInSeconds = healthCheckIntervalInSeconds;
        this.healthCheckUrl = healthCheckUrl;
        this.healthCheckPort = healthCheckPort;
        this.failureThresholdTimes = failureThresholdTimes;
        this.serverList = new ArrayList<>();
        this.activeServerList = new ArrayList<>();
    }

    public void registerServer(Server server) {
        server.ping(server.host, server.port);
        if (server.getHealthStatus()) {
            if(!this.serverList.contains(server)) {
                this.serverList.add(server);
            }
            if(!this.activeServerList.contains(server)) {
                this.activeServerList.add(server);
            }
        }
    }

    public void deRegisterServer(Server server) {
        if (this.serverList.contains(server)) {
            this.serverList.remove(server);
        }
        if (this.activeServerList.contains(server)) {
            this.activeServerList.remove(server);
        }
    }

    public void performHealthCheck() {
        for (Server serverObj:this.serverList) {
            if (serverObj.ping(serverObj.host, serverObj.port) && !this.activeServerList.contains(serverObj)) {
                this.activeServerList.add(serverObj);
            } else if (this.activeServerList.contains(serverObj)) {
                this.activeServerList.remove(serverObj);
            }
        }
    }

}
