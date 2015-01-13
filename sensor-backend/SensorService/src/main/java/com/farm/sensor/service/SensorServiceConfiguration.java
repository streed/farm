package com.farm.sensor.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;



public class SensorServiceConfiguration extends Configuration {
    @NotEmpty
    private String redisServer;

    @JsonProperty
    public String getRedisHost() {
        return redisServer;
    }

}
