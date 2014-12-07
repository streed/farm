package com.farm.sensor.service.managers;

import com.farm.sensor.service.exceptions.SensorServiceException;
import com.farm.sensor.data.models.SensorSlug;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.api.RpcClient;
import org.apache.flume.event.EventBuilder;

import java.nio.charset.Charset;
import java.util.List;

public class SensorFlumeManager {
    private final RpcClient rpcClient;
    private final ObjectMapper objectMapper;
    private final List<SensorSlug> flumeBatch = Lists.newArrayList();

    @Inject
    public SensorFlumeManager(final RpcClient rpcClient, final ObjectMapper objectMapper) {
        this.rpcClient = rpcClient;
        this.objectMapper = objectMapper;
    }

    public synchronized void publish(final SensorSlug sensorSlug) throws SensorServiceException {
        flumeBatch.add(sensorSlug);
        if (flumeBatch.size() >= rpcClient.getBatchSize()) {
            sendFlumeBatch();
        }
    }

    private void sendFlumeBatch() throws SensorServiceException {
        try {
            List<Event> events = Lists.newArrayList();

            for (SensorSlug slug: flumeBatch) {
                String jsonSensorSlug = objectMapper.writeValueAsString(slug);
                Event event = EventBuilder.withBody(jsonSensorSlug, Charset.forName("UTF-8"));
                events.add(event);
            }

            rpcClient.appendBatch(events);
            flumeBatch.clear();
        } catch (JsonProcessingException | EventDeliveryException exception) {
            throw new SensorServiceException(exception.getMessage());
        }
    }
}
