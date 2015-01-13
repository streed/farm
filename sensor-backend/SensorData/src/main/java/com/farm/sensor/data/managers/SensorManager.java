package com.farm.sensor.data.managers;

import com.farm.sensor.data.ReadingsTable;
import com.farm.sensor.data.exceptions.SensorServiceException;
import com.farm.sensor.data.models.SensorSlug;
import com.farm.sensor.data.rowkeys.SensorSlugRowKey;
import com.google.inject.Inject;

import java.io.IOException;
import java.util.List;

public class SensorManager {
    private final SensorFlumeManager sensorFlumeManager;
    private final ReadingsTable readingsTable;

    @Inject
    public SensorManager(final SensorFlumeManager sensorFlumeManager, final ReadingsTable readingsTable) {
        this.sensorFlumeManager = sensorFlumeManager;
        this.readingsTable = readingsTable;
    }

    public void publish(final SensorSlug sensorSlug) throws SensorServiceException, IOException {
        sensorFlumeManager.publish(sensorSlug);
        readingsTable.saveSlug(sensorSlug);
    }

    public SensorSlug getSensorSlug(final int ownerId, final int sensorId) throws IOException {
        return readingsTable.getSlug(new SensorSlugRowKey(ownerId, sensorId));
    }

    public List<SensorSlug> getSensorSlugsForOwner(final int ownerId) throws IOException {
        return readingsTable.getSlugsForOwner(new SensorSlugRowKey(ownerId));
    }
}
