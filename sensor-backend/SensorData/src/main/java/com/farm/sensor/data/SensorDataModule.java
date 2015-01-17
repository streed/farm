package com.farm.sensor.data;

import com.google.common.collect.Sets;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

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

    @Provides
    @Named("com.farm.publish.channels")
    public Set<String> providesPublishChannels() {
        return Sets.newHashSet("slugs");
    }

    @Provides
    public Configuration providesHbaseConfiguration() {
        return HBaseConfiguration.create();
    }
}
