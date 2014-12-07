package com.farm.sensor.data.rowkeys;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.DataInputBuffer;
import org.apache.hadoop.io.DataOutputBuffer;

import java.io.IOException;

public class SensorSlugRowKey extends AbstractRowKey {
    private final HashFunction hashFunction = Hashing.murmur3_32();

    private int ownerId;
    private long timestamp;

    public SensorSlugRowKey(final int ownerId, final long timestamp) {
        this.ownerId = ownerId;
        this.timestamp = timestamp;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public long getTimestamp() {
        return timestamp;
    }


    protected void write(DataOutputBuffer dataOutputBuffer) throws IOException {
        dataOutputBuffer.writeInt(hashFunction.hashBytes(Bytes.toBytes(ownerId)).asInt());
        dataOutputBuffer.writeInt(ownerId);
        dataOutputBuffer.writeLong(Long.MAX_VALUE - timestamp);
    }

    protected void read(DataInputBuffer dataInputBuffer) throws IOException {
        dataInputBuffer.readInt();

        ownerId = dataInputBuffer.readInt();
        timestamp = Long.MAX_VALUE - dataInputBuffer.readLong();
    }
}
