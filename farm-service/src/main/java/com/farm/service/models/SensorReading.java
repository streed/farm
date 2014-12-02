package com.farm.service.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SensorReading {
    private long sensorId;
    private long timestamp;
    private double value;

    public SensorReading() {
    }

    public SensorReading(final long sensorId, final long timestamp, final long value) {
        this.sensorId = sensorId;
        this.timestamp = timestamp;
        this.value = value;
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
}
