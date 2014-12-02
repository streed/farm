package com.farm.service.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import java.util.Collection;

public class SensorSlug {
    private int teamId;
    private ImmutableList<SensorReading> readings;

    public SensorSlug() {
    }

    public SensorSlug(final int teamId, final Collection<SensorReading> readings) {
        this.teamId = teamId;
        this.readings = ImmutableList.copyOf(readings);
    }

    @JsonProperty
    public int getTeamId() {
        return teamId;
    }

    @JsonProperty
    public ImmutableList<SensorReading> getReadings() {
        return readings;
    }
}
