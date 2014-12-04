package com.farm.sensor.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;


public class SensorServiceConfiguration extends Configuration {
    @NotEmpty
    private List<String> flumeServers;

    @JsonProperty
    public List<String> getFlumeServers() {
        return flumeServers;
    }

    private int flumeBackOff = 10000;

    @JsonProperty
    public int getFlumeBackOff() {
        return flumeBackOff;
    }
}
