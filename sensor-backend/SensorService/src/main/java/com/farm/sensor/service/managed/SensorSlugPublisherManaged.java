package com.farm.sensor.service.managed;

import com.farm.sensor.data.managers.SensorSlugPublisher;
import com.google.inject.Inject;
import io.dropwizard.lifecycle.Managed;

public class SensorSlugPublisherManaged implements Managed {
    private final SensorSlugPublisher sensorSlugPublisher;

    @Inject
    public SensorSlugPublisherManaged(final SensorSlugPublisher sensorSlugPublisher) {
        this.sensorSlugPublisher = sensorSlugPublisher;
    }

    @Override
    public void start() throws Exception {
        //TODO: Check status here
    }

    @Override
    public void stop() throws Exception {
        sensorSlugPublisher.close();
    }
}
