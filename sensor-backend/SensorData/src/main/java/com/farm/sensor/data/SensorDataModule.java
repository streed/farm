package com.farm.sensor.data;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class SensorDataModule extends AbstractModule {

    @Override
    public void configure() {

    }

    @Provides
    @Singleton
    public JedisPool providesJedisPool() {
        //TODO: Fix this so it's configurable
        return new JedisPool(new JedisPoolConfig(), "localhost");
    }
}
