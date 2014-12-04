package com.farm.sensor.service;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.apache.flume.api.RpcClient;
import org.apache.flume.api.RpcClientFactory;

import java.util.List;
import java.util.Properties;

public class SensorServiceModule extends AbstractModule {
    @Override
    protected void configure() {
    }

    @Inject
    @Provides
    @Singleton
    public RpcClient providesRpcCLient(final SensorServiceConfiguration sensorServiceConfiguration) {
        return RpcClientFactory.getInstance(buildProperties(sensorServiceConfiguration));
    }

    private Properties buildProperties(final SensorServiceConfiguration sensorServiceConfiguration) {
        List<String> hosts = sensorServiceConfiguration.getFlumeServers();
        Properties props = new Properties();
        props.put("client.type", "default_loadbalancer");
        List<String> hostAliases = generateHostAliases(hosts.size());
        props.put("hosts", Joiner.on(' ').join(hostAliases));

        for (String host: hosts) {
            props.put(String.format("hosts.%s", hostAliases.get(hosts.indexOf(host))), host);
        }

        props.put("host-selector", "random");
        props.put("backoff", "true");
        props.put("maxBackoff", Integer.toString(sensorServiceConfiguration.getFlumeBackOff()));

        return props;
    }

    private List<String> generateHostAliases(int numHosts) {
        List<String> hostAliases = Lists.newArrayList();

        for (int i = 0; i < numHosts; i++ ) {
            hostAliases.add(String.format("host-%s", i));
        }

        return hostAliases;
    }
}
