package com.farm.sensor.service;

import com.google.inject.AbstractModule;
import com.farm.sensor.data.SensorDataModule;

public class SensorServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new SensorDataModule());
    }
}
