package com.farm.sensor.data.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import java.util.Collection;

public class SensorSlug {
    private int ownerId;
    private long timestamp;
    private ImmutableList<SensorReading> readings;

    public SensorSlug() {
    }

    public SensorSlug(final int ownerId, final long timestamp, final Collection<SensorReading> readings) {
        this.ownerId = ownerId;
        this.timestamp = timestamp;
        this.readings = ImmutableList.copyOf(readings);
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
    public ImmutableList<SensorReading> getReadings() {
        return readings;
    }
}
