package com.farm.sensor.service;

import com.google.inject.AbstractModule;
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

    @Provides
    @Singleton
    public JedisPool providesJedisPool() {
        //TODO: Fix this so it's configurable
        return new JedisPool(new JedisPoolConfig(), "localhost");
    }

    @Provides
    public Configuration providesHbaseConfiguration() {
        return HBaseConfiguration.create();
    }
}
