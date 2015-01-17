package com.farm.sensor.service.healthchecks;

import com.google.inject.Inject;
import com.hubspot.dropwizard.guice.InjectableHealthCheck;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisHealthCheck extends InjectableHealthCheck {
    private final JedisPool jedisPool;

    @Inject
    public RedisHealthCheck(final JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public String getName() {
        return "Redis Check";
    }

    @Override
    public Result check() throws Exception {
        Jedis jedis = jedisPool.getResource();

        if (jedis.isConnected()) {
            jedisPool.returnResource(jedis);
            return Result.healthy();
        } else {
            return Result.unhealthy("Not connected to Redis server");
        }
    }
}
