package com.farm.service.managers;

import com.farm.service.models.SensorSlug;
import com.farm.service.views.FarmView;
import com.farm.service.views.FieldView;
import com.google.inject.Inject;

public class SensorManager {
    @Inject
    public SensorManager() {}

    public void publish(final SensorSlug sensorSlug) {
        //publish slug to flume
    }

    public FarmView getFarmView(final int farmId) {
        return null;
    }

    public FieldView getFieldView(final int farmId, final int fieldId) {
        return null;
    }

    public SensorSlug getSensorSlug(final int farmId, final int fieldId, final int sensorId) {
        return null;
    }
}
