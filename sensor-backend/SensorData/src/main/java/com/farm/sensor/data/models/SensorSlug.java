package com.farm.sensor.data.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SensorSlug {
    private int ownerId;
    private long timestamp;
    private List<SensorReading> readings;

    public SensorSlug() {
    }

    public SensorSlug(final int ownerId, final long timestamp, final List<SensorReading> readings) {
        this.ownerId = ownerId;
        this.timestamp = timestamp;
        this.readings = readings;
    }

    @JsonProperty
    public int getOwnerId() {
        return ownerId;
    }

    @JsonProperty
    public long getTimestamp() {
        return timestamp;
    }

    @JsonProperty
    public List<SensorReading> getReadings() {
        return readings;
    }
}
