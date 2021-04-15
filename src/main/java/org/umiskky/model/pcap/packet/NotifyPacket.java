package org.umiskky.model.pcap.packet;

import lombok.Getter;
import org.pcap4j.packet.AbstractPacket;
import org.pcap4j.packet.IllegalRawDataException;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.factory.PacketFactories;
import org.pcap4j.packet.namednumber.NotApplicable;
import org.pcap4j.util.ByteArrays;
import org.umiskky.model.pcap.util.Timestamp;
import org.umiskky.model.pcap.util.Uuid;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

import static org.pcap4j.util.ByteArrays.LONG_SIZE_IN_BYTES;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/15
 */
public final class NotifyPacket extends AbstractPacket{

    @Serial
    private static final long serialVersionUID = -6967892952063155095L;

    private final NotifyPacket.NotifyHeader header;
    private final Packet payload;

    private NotifyPacket(byte[] rawData, int offset, int length) throws IllegalRawDataException {
        this.header = new NotifyPacket.NotifyHeader(rawData, offset, length);
        int payloadLength = length - header.length();
        int payloadOffset = offset + header.length();
        if(payloadLength > 0){
            this.payload = (Packet) PacketFactories.getFactory(Packet.class, NotApplicable.class).newInstance(rawData, payloadOffset, payloadLength, new NotApplicable[]{NotApplicable.UNKNOWN});
        }else{
            this.payload = null;
        }
    }

    private NotifyPacket(NotifyPacket.Builder builder) {
        this.header = new NotifyPacket.NotifyHeader(builder);
        this.payload = builder.payloadBuilder != null ? builder.payloadBuilder.build() : null;
    }

    public static NotifyPacket newPacket(byte[] rawData, int offset, int length)
            throws IllegalRawDataException {
        ByteArrays.validateBounds(rawData, offset, length);
        return new NotifyPacket(rawData, offset, length);
    }

    @Override
    public NotifyPacket.NotifyHeader getHeader() {
        return header;
    }

    @Override
    public Packet getPayload() {
        return payload;
    }

    @Override
    public NotifyPacket.Builder getBuilder() {
        return new NotifyPacket.Builder(this);
    }


    /** Builder*/
    public static final class Builder extends AbstractPacket.AbstractBuilder {
        private Uuid uuid;
        private Timestamp timestamp;
        private Packet.Builder payloadBuilder;

        /** */
        public Builder() {}

        private Builder(NotifyPacket packet) {
            this.uuid = packet.header.uuid;
            this.timestamp = packet.header.timestamp;
            this.payloadBuilder = packet.payload != null ? packet.payload.getBuilder() : null;
        }

        public NotifyPacket.Builder uuid(Uuid uuid) {
            this.uuid = uuid;
            return this;
        }

        public NotifyPacket.Builder timestamp(Timestamp timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        @Override
        public NotifyPacket.Builder payloadBuilder(Packet.Builder payloadBuilder) {
            this.payloadBuilder = payloadBuilder;
            return this;
        }

        @Override
        public Packet.Builder getPayloadBuilder() {
            return payloadBuilder;
        }

        @Override
        public NotifyPacket build() {
            return new NotifyPacket(this);
        }
    }

    /** Header*/
    public static final class NotifyHeader extends AbstractPacket.AbstractHeader {

        @Serial
        private static final long serialVersionUID = 6197144485115529913L;
        private static final int SRC_UUID_OFFSET = 0;
        private static final int SRC_UUID_SIZE = 32;
        private static final int TIMESTAMP_OFFSET = SRC_UUID_OFFSET + SRC_UUID_SIZE;
        private static final int TIMESTAMP_SIZE = LONG_SIZE_IN_BYTES;

        private static final int NOTIFY_HEADER_SIZE = TIMESTAMP_OFFSET + TIMESTAMP_SIZE;

        @Getter
        private final Uuid uuid;
        @Getter
        private final Timestamp timestamp;

        private NotifyHeader(byte[] rawData, int offset, int length) throws IllegalRawDataException {
            if (length < NOTIFY_HEADER_SIZE) {
                StringBuilder sb = new StringBuilder(100);
                sb.append("The data is too short to build an Notify header(")
                        .append(NOTIFY_HEADER_SIZE)
                        .append(" bytes). data: ")
                        .append(ByteArrays.toHexString(rawData, " "))
                        .append(", offset: ")
                        .append(offset)
                        .append(", length: ")
                        .append(length);
                throw new IllegalRawDataException(sb.toString());
            }
            this.uuid = Uuid.getInstance(ByteArrays.getSubArray(rawData, SRC_UUID_OFFSET + offset, SRC_UUID_SIZE));
            this.timestamp = Timestamp.getInstance(ByteArrays.getLong(rawData, TIMESTAMP_OFFSET + offset));
        }

        private NotifyHeader(NotifyPacket.Builder builder) {
            this.uuid = builder.uuid;
            this.timestamp = builder.timestamp;
        }

        @Override
        protected List<byte[]> getRawFields() {
            List<byte[]> rawFields = new ArrayList<byte[]>();
            rawFields.add(uuid.toByteArray());
            rawFields.add(ByteArrays.toByteArray(timestamp.getTimestamp()));
            return rawFields;
        }

        @Override
        protected String buildString() {
            StringBuilder sb = new StringBuilder();
            String ls = System.getProperty("line.separator");

            sb.append("[NotifyPacket Header (").append(length()).append(" bytes)]").append(ls);
            sb.append("  Uuid: ").append(uuid).append(ls);
            sb.append("  Timestamp: ").append(timestamp).append(ls);

            return sb.toString();
        }

        @Override
        public int length() {
            return NOTIFY_HEADER_SIZE;
        }
    }
}
