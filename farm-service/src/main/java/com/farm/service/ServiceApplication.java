package com.farm.service;

import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ServiceApplication extends Application<ServiceConfiguration> {

    @Override
    public String getName() {
        return "farm-service";
    }

    @Override
    public void initialize(Bootstrap<ServiceConfiguration> bootstrap) {
        GuiceBundle<ServiceConfiguration> guiceBundle = GuiceBundle.<ServiceConfiguration>newBuilder()
                .enableAutoConfig(getClass().getPackage().getName())
                .addModule(new ServiceModule())
                .setConfigClass(ServiceConfiguration.class)
                .build();

        bootstrap.addBundle(guiceBundle);
    }

    @Override
    public void run(ServiceConfiguration serviceConfiguration, Environment environment) throws Exception {
    }
}
