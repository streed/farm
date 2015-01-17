package com.farm.sensor.data.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

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

    @Override
    public String toString() {
        return Objects.toStringHelper(SensorSlug.class)
                .add("OwnerId", ownerId)
                .add("timestamp", timestamp)
                .add("readings", readings)
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof SensorSlug)) {
            return false;
        }

        SensorSlug b = (SensorSlug)obj;

        return ownerId == b.ownerId &&
                timestamp == b.timestamp &&
                Objects.equal(readings, b.readings);
    }
}
