package com.farm.sensor.service.hbase;

import com.farm.sensor.service.hbase.utils.SensorCreateSchema;
import com.farm.sensor.service.models.SensorSlug;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;

import java.io.IOException;

public class ReadingsTable {
    private final Configuration configuration;
    private final ObjectMapper objectMapper;
    private HTable hTable = null;

    @Inject
    public ReadingsTable(Configuration configuration, ObjectMapper objectMapper) {
        this.configuration = configuration;
        this.objectMapper = objectMapper;
    }

    public void connect() throws IOException {
        this.hTable = new HTable(configuration, SensorCreateSchema.TableNames.READINGS.getName());
    }

    public SensorSlug getSlug(final SensorSlugRowKey rowKey) throws IOException {
        Get get = new Get(rowKey.getBytes());
        Result result = hTable.get(get);

        return objectMapper.readValue(
                result.getValue(
                        SensorCreateSchema.ColumnFamiles.READINGS.getName().getBytes(),
                        "reading".getBytes()),
                SensorSlug.class);
    }
}
