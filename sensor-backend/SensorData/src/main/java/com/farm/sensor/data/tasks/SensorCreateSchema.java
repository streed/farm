package com.farm.sensor.data.tasks;

import com.farm.tasks.Task;
import com.google.inject.Inject;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class SensorCreateSchema implements Task {
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

    @Inject
    public SensorCreateSchema(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Options getOptions() {
        return new Options();
    }

    @Override
    public void setup(CommandLine commandLine) {
    }

    public void run() throws Exception {
        final HBaseAdmin admin = new HBaseAdmin(configuration);
        HTableDescriptor table = new HTableDescriptor(TableName.valueOf(TableNames.READINGS.getName()));

        table.addFamily(new HColumnDescriptor(ColumnFamiles.READINGS_BODY.getName()));
        admin.createTable(table);
        admin.close();
    }
}
