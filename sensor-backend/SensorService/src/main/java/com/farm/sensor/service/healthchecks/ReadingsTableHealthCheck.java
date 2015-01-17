package com.farm.sensor.service.healthchecks;

import com.farm.sensor.data.tables.ReadingsTable;
import com.google.inject.Inject;
import com.hubspot.dropwizard.guice.InjectableHealthCheck;

public class ReadingsTableHealthCheck extends InjectableHealthCheck {

    private final ReadingsTable readingsTable;

    @Inject
    public ReadingsTableHealthCheck(final ReadingsTable readingsTable) {
        this.readingsTable = readingsTable;
    }

    @Override
    public String getName() {
        return "ReadingsTableHealthCheck";
    }

    @Override
    public Result check() throws Exception {
        if (readingsTable.isConnected()) {
            return Result.healthy();
        } else {
            return Result.unhealthy("ReadingsTable is not connected");
        }
    }
}
