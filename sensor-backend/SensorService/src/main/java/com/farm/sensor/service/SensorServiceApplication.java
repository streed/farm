package com.farm.sensor.service;

import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class SensorServiceApplication extends Application<SensorServiceConfiguration> {

    @Override
    public String getName() {
        return "farm-service";
    }

    @Override
    public void initialize(Bootstrap<SensorServiceConfiguration> bootstrap) {
        GuiceBundle<SensorServiceConfiguration> guiceBundle = GuiceBundle.<SensorServiceConfiguration>newBuilder()
                .enableAutoConfig(getClass().getPackage().getName())
                .addModule(new SensorServiceModule())
                .enableAutoConfig(Package.getPackage("com.farm.sensor.service").getName())
                .setConfigClass(SensorServiceConfiguration.class)
                .build();

        bootstrap.addBundle(guiceBundle);
    }

    @Override
    public void run(SensorServiceConfiguration sensorServiceConfiguration, Environment environment) throws Exception {
    }
}
