package com.farm.sensor.data.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import java.util.List;

public class SensorReading {
    private long sensorId;
    private long timestamp;
    private double value;
    private List<String> tags;

    public SensorReading() {
    }

    public SensorReading(final long sensorId, final long timestamp, final long value) {
        this(sensorId, timestamp, value, Lists.<String>newArrayList());
    }

    public SensorReading(final long sensorId, final long timestamp , final long value, final List<String> tags) {
        this.sensorId = sensorId;
        this.timestamp = timestamp;
        this.value = value;
        this.tags = tags;
    }

    @JsonProperty
    public long getSensorId() {
        return sensorId;
    }

    @JsonProperty
    public long getTimestamp() {
        return timestamp;
    }

    @JsonProperty
    public double getValue() {
        return value;
    }

    @JsonProperty
    public List<String> getTags() {
        return tags;
    }
}
