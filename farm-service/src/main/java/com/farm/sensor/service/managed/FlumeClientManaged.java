package com.farm.sensor.service.managed;

import com.google.inject.Inject;
import io.dropwizard.lifecycle.Managed;
import org.apache.flume.api.RpcClient;

public class FlumeClientManaged implements Managed {
    private final RpcClient rpcClient;

    @Inject
    public FlumeClientManaged(final RpcClient rpcClient) {
        this.rpcClient = rpcClient;
    }

    @Override
    public void start() throws Exception {
        if (!rpcClient.isActive()) {
           throw new Exception("RpcClient is not active");
        }
    }

    @Override
    public void stop() throws Exception {
        rpcClient.close();
    }
}
