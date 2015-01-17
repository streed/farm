package com.farm.sensor.data.tasks;

import com.farm.sensor.data.SensorDataModule;
import com.farm.tasks.TaskRunner;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class SensorTaskRunner extends TaskRunner {

    @Override
    public Injector getInjector() {
        return Guice.createInjector(new SensorDataModule());
    }

    @Override
    public void configure() {
        register("createschema", "Create the hbase schema", SensorCreateSchema.class);
    }

    public static void main(String [] args) throws Exception {
        new SensorTaskRunner().run(args);
    }
}
