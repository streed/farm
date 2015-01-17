package com.farm.sensor.service;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import com.farm.sensor.data.SensorDataModule;

public class SensorServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new SensorDataModule());
    }


    @Provides
    public Configuration providesHbaseConfiguration() {
        return HBaseConfiguration.create();
    }
}
