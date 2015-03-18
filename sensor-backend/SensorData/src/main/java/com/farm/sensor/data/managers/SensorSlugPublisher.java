package com.farm.sensor.data.managers;

import com.farm.sensor.data.exceptions.SensorServiceException;
import com.farm.sensor.data.models.SensorSlug;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

@Singleton
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

    //TODO: Make this an executor pool, a single thread should suffice for now.
    private void startPublisher() {
        publisher = new Publisher(objectMapper, jedisPool, channels);
        Thread thread = threadFactory.newThread(publisher);
        thread.setDaemon(true);
        thread.start();
    }

    private static class Publisher implements Runnable {
        private final Logger LOG = LoggerFactory.getLogger(Publisher.class);
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
                LOG.error("Error while putting SensorSlug {} onto the queue", sensorSlug, exception);
                cleanup();
            }
        }

        public void close() {
            running.set(false);
        }

        @Override
        public void run() {
            LOG.info("Starting the Publisher processing thread");
            try {
                while (running.get()) {
                    SensorSlug slug = slugQueue.take();
                    for (String channel : channels) {
                        this.jedis.publish(channel, objectMapper.writeValueAsString(slug));
                    }
                }
            } catch (InterruptedException | JsonProcessingException exception) {
                LOG.error("Something broke while trying to publish to {} channels", channels, exception);
            }

            LOG.info("Cleaning up the Publisher did we exit gracefully? {}", running.get());
            cleanup();
        }

        private void cleanup() {
            jedisPool.returnResource(jedis);
        }
    }
}
