package com.farm.sensor.data.managers;

import com.farm.sensor.data.exceptions.SensorServiceException;
import com.farm.sensor.data.models.SensorSlug;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;


public class SensorSlugPublisher {
    private final JedisPool jedisPool;
    private final ObjectMapper objectMapper;
    private final ThreadFactory threadFactory;
    private final Set<String> channels;

    private Publisher publisher;

    @Inject
    public SensorSlugPublisher(final JedisPool jedisPool, final ObjectMapper objectMapper, @Named("com.farm.publish.channels") final Set<String> publishChannels) {
        this.jedisPool = jedisPool;
        this.objectMapper = objectMapper;
        this.threadFactory = MoreExecutors.platformThreadFactory();
        this.channels = publishChannels;

        startPublisher();
    }

    public void publish(final SensorSlug sensorSlug) throws SensorServiceException {
        publisher.publish(sensorSlug);
    }

    public void close() {
        publisher.close();
    }

    private void startPublisher() {
        publisher = new Publisher(objectMapper, jedisPool, channels);
        threadFactory.newThread(publisher);
    }

    private static class Publisher implements Runnable {

        private final ObjectMapper objectMapper;
        private final Jedis jedis;
        private final JedisPool jedisPool;
        private BlockingQueue<SensorSlug> slugQueue;
        private final Collection<String> channels;
        private final AtomicBoolean running = new AtomicBoolean(true);

        public Publisher(final ObjectMapper objectMapper, final JedisPool jedisPool, final Collection<String> channels) {
            this.objectMapper = objectMapper;
            this.jedisPool = jedisPool;
            this.slugQueue = new LinkedBlockingQueue<>();
            this.channels = channels;

            this.jedis = jedisPool.getResource();
        }

        public void publish(SensorSlug sensorSlug) {
            try {
                slugQueue.put(sensorSlug);
            } catch (InterruptedException exception) {
                cleanup();
            }
        }

        public void close() {
            running.set(false);
        }

        @Override
        public void run() {
            try {
                while (running.get()) {
                    SensorSlug slug = slugQueue.take();
                    for (String channel : channels) {
                        this.jedis.publish(channel, objectMapper.writeValueAsString(slug));
                    }
                }
            } catch (InterruptedException | JsonProcessingException exception) {
            }

            cleanup();
        }

        private void cleanup() {
            jedisPool.returnResource(jedis);
        }
    }
}
