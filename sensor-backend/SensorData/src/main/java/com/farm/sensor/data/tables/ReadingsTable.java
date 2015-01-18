package com.farm.sensor.data.tables;

import com.farm.sensor.data.rowkeys.SensorSlugRowKey;
import com.farm.sensor.data.models.SensorSlug;
import com.farm.sensor.data.tasks.SensorCreateSchema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@Singleton
public class ReadingsTable {
    private final Logger LOG = LoggerFactory.getLogger(ReadingsTable.class);
    private final Configuration configuration;
    private final ObjectMapper objectMapper;
    private HTable hTable = null;

    @Inject
    public ReadingsTable(Configuration configuration, ObjectMapper objectMapper) {
        this.configuration = configuration;
        this.objectMapper = objectMapper;
    }

    public void connect() throws IOException {
        hTable = new HTable(configuration, SensorCreateSchema.TableNames.READINGS.getName());
    }

    public void shutdown() throws Exception {
        if (hTable == null) {
            throw new RuntimeException("connect() was not called on the ReadingsTable, cannont shutdown something that was never alive");
        } else {
            hTable.close();
        }
    }

    public boolean isConnected() throws IOException {
        //TODO: Fix this because it's depreceated.
        return hTable.getConnection() != null;
    }

    public SensorSlug getSlug(final SensorSlugRowKey rowKey) throws IOException {
        Get get = new Get(rowKey.toBytes());
        Result result = hTable.get(get);

        return readResult(result);
    }

    public List<SensorSlug> getSlugsForOwner(final SensorSlugRowKey rowKey) throws IOException {
        Scan scan = new Scan();

        scan.setStartRow(rowKey.toBytes());
        scan.addFamily(SensorCreateSchema.ColumnFamiles.READINGS_BODY.getName().getBytes());

        ResultScanner results = hTable.getScanner(scan);

        List<SensorSlug> slugs = Lists.newArrayList();
        for (Result result: results) {
            slugs.add(readResult(result));
        }

        return slugs;
    }

    public void saveSlug(final SensorSlug sensorSlug) throws IOException {
        SensorSlugRowKey sensorSlugRowKey = new SensorSlugRowKey(sensorSlug.getOwnerId(), sensorSlug.getTimestamp());
        Put put = new Put(sensorSlugRowKey.toBytes());

        put.add(SensorCreateSchema.ColumnFamiles.READINGS_BODY.getName().getBytes(),
                "reading".getBytes(),
                objectMapper.writeValueAsBytes(sensorSlug));

        Get get = new Get(sensorSlugRowKey.toBytes());
        if (!hTable.exists(get)) {
            hTable.put(put);
        } else {
            LOG.info("Got duplicate write for: {}", sensorSlug);
        }
    }

    private SensorSlug readResult(Result result) throws IOException {
        return objectMapper.readValue(
                result.getValue(
                        SensorCreateSchema.ColumnFamiles.READINGS_BODY.getName().getBytes(),
                        "reading".getBytes()),
                SensorSlug.class);
    }
}
