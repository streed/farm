package com.farm.sensor.data;

import com.farm.sensor.data.rowkeys.SensorSlugRowKey;
import com.farm.sensor.data.utils.SensorCreateSchema;
import com.farm.sensor.data.models.SensorSlug;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;
import java.util.List;

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
        Get get = new Get(rowKey.toBytes());
        Result result = hTable.get(get);

        return objectMapper.readValue(
                result.getValue(
                        SensorCreateSchema.ColumnFamiles.READINGS_BODY.getName().getBytes(),
                        "reading".getBytes()),
                SensorSlug.class);
    }

    public List<SensorSlug> getSlugsForOwner(final SensorSlugRowKey rowKey) {
        Scan scan = new Scan()
    }

    public void saveSlug(final SensorSlug sensorSlug) throws IOException {
        SensorSlugRowKey sensorSlugRowKey = new SensorSlugRowKey(sensorSlug.getOwnerId(), sensorSlug.getTimestamp());
        Put put = new Put(sensorSlugRowKey.toBytes());

        put.add("reading".getBytes(),
                SensorCreateSchema.ColumnFamiles.READINGS_BODY.getName().getBytes(),
                objectMapper.writeValueAsBytes(sensorSlug));

        hTable.checkAndPut(sensorSlugRowKey.toBytes(),
                "reading".getBytes(),
                SensorCreateSchema.ColumnFamiles.READINGS_BODY.getName().getBytes(),
                null,
                put);
    }
}
