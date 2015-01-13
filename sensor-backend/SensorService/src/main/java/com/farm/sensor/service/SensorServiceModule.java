package com.farm.sensor.service;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class SensorServiceModule extends AbstractModule {
    @Override
    protected void configure() {
    }

    @Inject
    @Provides
    @Singleton
    public JedisPool providesJedisPool(final SensorServiceConfiguration sensorServiceConfiguration) {
        return new JedisPool(new JedisPoolConfig(), sensorServiceConfiguration.getRedisHost());
    }

    @Provides
    public Configuration providesHbaseConfiguration() {
        return HBaseConfiguration.create();
    }
}
