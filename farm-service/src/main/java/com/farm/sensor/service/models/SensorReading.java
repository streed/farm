package com.farm.sensor.service.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

public class SensorReading {
    private long sensorId;
    private long timestamp;
    private double value;
    private ImmutableList<String> tags;

    public SensorReading() {
    }

    public SensorReading(final long sensorId, final long timestamp, final long value) {
        this(sensorId, timestamp, value, ImmutableList.<String>of());
    }

    public SensorReading(final long sensorId, final long timestamp , final long value, final ImmutableList<String> tags) {
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
    public ImmutableList<String> getTags() {
        return tags;
    }
}
