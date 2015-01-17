package com.farm.sensor.service.managed;

import com.farm.sensor.data.tables.ReadingsTable;
import com.google.inject.Inject;
import io.dropwizard.lifecycle.Managed;

public class ReadingsTableManaged implements Managed {
    private final ReadingsTable readingsTable;

    @Inject
    public ReadingsTableManaged(final ReadingsTable readingsTable) {
        this.readingsTable = readingsTable;
    }

    @Override
    public void start() throws Exception {
        readingsTable.connect();
    }

    @Override
    public void stop() throws Exception {
        readingsTable.shutdown();
    }
}
