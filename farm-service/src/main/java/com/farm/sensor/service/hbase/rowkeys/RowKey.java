package com.farm.sensor.service.hbase.rowkeys;

import java.io.IOException;

public interface RowKey {
    public byte [] toBytes() throws IOException;
    public void fromBytes(byte [] bytes) throws IOException;

}
