package com.farm.sensor.data.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
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

    @Override
    public String toString() {
        return Objects.toStringHelper(SensorReading.class)
                .add("sensorId", sensorId)
                .add("timestamp", timestamp)
                .add("value", value)
                .add("tags", tags)
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof SensorReading)) {
            return false;
        }

        SensorReading b = (SensorReading)obj;

        return sensorId == b.sensorId &&
                timestamp == b.timestamp &&
                Double.compare(value, b.value) == 0 &&
                Objects.equal(tags, b.tags);
    }
}
