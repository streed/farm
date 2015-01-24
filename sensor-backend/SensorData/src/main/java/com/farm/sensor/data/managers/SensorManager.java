package com.farm.sensor.data.managers;

import com.farm.sensor.data.tables.ReadingsTable;
import com.farm.sensor.data.exceptions.SensorServiceException;
import com.farm.sensor.data.models.SensorSlug;
import com.farm.sensor.data.rowkeys.SensorSlugRowKey;
import com.google.common.base.Optional;
import com.google.inject.Inject;

import java.io.IOException;
import java.util.List;

public class SensorManager {
    private final ReadingsTable readingsTable;
    private final SensorSlugPublisher sensorSlugPublisher;

    @Inject
    public SensorManager(final ReadingsTable readingsTable, final SensorSlugPublisher sensorSlugPublisher) {
        this.readingsTable = readingsTable;
        this.sensorSlugPublisher = sensorSlugPublisher;
    }

    public void publish(final SensorSlug sensorSlug) throws SensorServiceException, IOException {
        readingsTable.saveSlug(sensorSlug);
        sensorSlugPublisher.publish(sensorSlug);
    }

    public Optional<SensorSlug> getSensorSlug(final int ownerId, final long timestamp) throws IOException {
        return readingsTable.getSlug(new SensorSlugRowKey(ownerId, timestamp));
    }

    public List<SensorSlug> getSensorSlugsForOwner(final int ownerId) throws IOException {
        return readingsTable.getSlugsForOwner(new SensorSlugRowKey(ownerId, 0L));
    }
}
