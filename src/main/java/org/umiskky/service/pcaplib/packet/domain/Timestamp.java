package org.umiskky.service.pcaplib.packet.domain;

import java.io.Serial;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/15
 */
public class Timestamp implements Serializable {

    @Serial
    private static final long serialVersionUID = 4143254414458553248L;

    private final long timestamp;

    protected Timestamp(){
        timestamp = Instant.now().toEpochMilli();
    }

    protected Timestamp(long timestamp){
        this.timestamp = timestamp;
    }

    /**
     * @description The method getInstance is used to get an object of Class Timestamp.
     * @param
     * @return org.umiskky.model.pcap.util.Timestamp
     * @author umiskky
     * @date 2021/4/15-22:19
     */
    public static Timestamp getInstance(){
        return new Timestamp();
    }

    public static Timestamp getInstance(long timestamp){
        return new Timestamp(timestamp);
    }

    public static Timestamp getInstance(byte[] timestamp){
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(timestamp);
        buffer.flip();
        return Timestamp.getInstance(buffer.getLong());
    }

    /**
     * @description The method toByteArray is used to convert the timestamp from long to byte array.
     * @param
     * @return byte[]
     * @author umiskky
     * @date 2021/4/15-22:17
     */
    public byte[] toByteArray(){
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(timestamp);
        return buffer.array();
    }

    /**
     * @description The method getZonedDateTime is used to get ZonedDateTime.
     * @param
     * @return java.time.ZonedDateTime
     * @author umiskky
     * @date 2021/4/15-22:18
     */
    public ZonedDateTime getZonedDateTime(){
        Instant instant = Instant.ofEpochMilli(timestamp);
        return instant.atZone(ZoneId.systemDefault());
    }

    public ZonedDateTime getZonedDateTime(ZoneId zoneId){
        Instant instant = Instant.ofEpochMilli(timestamp);
        return instant.atZone(zoneId);
    }

    @Override
    public String toString() {
        return this.getZonedDateTime().toString();
    }

    public long getTimestamp() {
        return this.timestamp;
    }
}
