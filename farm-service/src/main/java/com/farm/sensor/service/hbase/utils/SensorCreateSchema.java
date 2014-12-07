package com.farm.sensor.service.hbase.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.io.compress.Compression.Algorithm;

import java.io.IOException;

public class SensorCreateSchema {
    public static enum TableNames {
        READINGS("sensor-readings-0");

        private String name;

        TableNames(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static enum ColumnFamiles {
        READINGS_BODY("readings-body");

        private String name;

        ColumnFamiles(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private final Configuration configuration;

    public SensorCreateSchema(Configuration configuration) {
        this.configuration = configuration;
    }

    public void createTables() throws IOException {
        final HBaseAdmin admin = new HBaseAdmin(configuration);
        HTableDescriptor table = new HTableDescriptor(TableName.valueOf(TableNames.READINGS.getName()));

        table.addFamily(new HColumnDescriptor(ColumnFamiles.READINGS_BODY.getName()).setCompressionType(Algorithm.SNAPPY));
        admin.createTable(table);
    }
}
