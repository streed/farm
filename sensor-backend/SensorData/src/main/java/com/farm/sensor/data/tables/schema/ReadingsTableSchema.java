package com.farm.sensor.data.tables.schema;

/**
 * Created by elchupa on 1/23/15.
 */
public class ReadingsTableSchema {
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
}
