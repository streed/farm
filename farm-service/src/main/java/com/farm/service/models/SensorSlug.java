package com.farm.service.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import java.util.Collection;

public class SensorSlug {
    private int farmId;
    private int fieldId;
    private ImmutableList<SensorReading> readings;

    public SensorSlug() {
    }

    public SensorSlug(final int farmId, final int fieldId, final Collection<SensorReading> readings) {
        this.farmId = farmId;
        this.fieldId = fieldId;
        this.readings = ImmutableList.copyOf(readings);
    }

    @JsonProperty
    public int getFarmId() {
        return farmId;
    }

    @JsonProperty
    public int getFieldId() {
        return fieldId;
    }

    @JsonProperty
    public ImmutableList<SensorReading> getReadings() {
        return readings;
    }
}
