package com.example.LoadBalancerNew.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Server {
    @JsonProperty("name")
    String name;

    @JsonProperty("host")
    String host;

    @JsonProperty("port")
    String port;

    @JsonProperty("health_status")
    Boolean healthStatus;

    public boolean ping(String host, String port) {
        //curl host:port
        // set healthStatus
        //return ping status
        return true;
    }

    public void assignRequest(int requestId) {
        // curl
    }

}
