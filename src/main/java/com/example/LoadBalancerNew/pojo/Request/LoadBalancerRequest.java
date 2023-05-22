package com.example.LoadBalancerNew.pojo.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoadBalancerRequest {
    String name;
    @JsonProperty("service_timeout_in_ms")
    Integer serviceTimeoutInMs;
    @JsonProperty("healthcheck_interval_in_seconds")
    Integer healthCheckIntervalInSeconds;
    @JsonProperty("healthcheck_url")
    String healthCheckUrl;
    @JsonProperty("healthcheck_port")
    Integer healthCheckPort;
    @JsonProperty("failure_threshold_times")
    Integer failureThresholdTimes;
}
