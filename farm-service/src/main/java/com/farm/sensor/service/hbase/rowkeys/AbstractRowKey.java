package com.farm.sensor.service.hbase.rowkeys;

import org.apache.hadoop.io.DataInputBuffer;
import org.apache.hadoop.io.DataOutputBuffer;

import java.io.IOException;

public abstract class AbstractRowKey implements RowKey {

    @Override
    public byte [] toBytes() throws IOException {
        DataOutputBuffer dataOutputBuffer = new DataOutputBuffer();
        write(dataOutputBuffer);
        return dataOutputBuffer.getData();
    }

    @Override
    public void fromBytes(byte [] bytes) throws IOException {
        DataInputBuffer dataInputBuffer = new DataInputBuffer();
        dataInputBuffer.reset(bytes, bytes.length);
        read(dataInputBuffer);
    }

    protected abstract void write(DataOutputBuffer row) throws IOException;
    protected abstract void read(DataInputBuffer row) throws IOException;

}
