package com.farm.sensor.service.managers;

import com.farm.sensor.service.exceptions.SensorServiceException;
import com.farm.sensor.service.models.SensorSlug;
import com.google.inject.Inject;

public class SensorManager {
    private final SensorFlumeManager sensorFlumeManager;

    @Inject
    public SensorManager(final SensorFlumeManager sensorFlumeManager) {
        this.sensorFlumeManager = sensorFlumeManager;
    }

    public void publish(final SensorSlug sensorSlug) throws SensorServiceException {
        sensorFlumeManager.publish(sensorSlug);
    }

    public SensorSlug getSensorSlug(final int ownerId, final int sensorId) {
        return null;
    }
}
