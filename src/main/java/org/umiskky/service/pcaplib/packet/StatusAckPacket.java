package org.umiskky.service.pcaplib.packet;

import org.pcap4j.packet.AbstractPacket;
import org.pcap4j.packet.IllegalRawDataException;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.factory.PacketFactories;
import org.pcap4j.packet.namednumber.NotApplicable;
import org.pcap4j.util.ByteArrays;
import org.umiskky.service.pcaplib.packet.domain.Uuid;
import org.umiskky.service.pcaplib.packet.namednumber.StatusAckPacketAuthorityCode;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

import static org.pcap4j.util.ByteArrays.BYTE_SIZE_IN_BYTES;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/15
 */
public final class StatusAckPacket extends AbstractPacket {

    @Serial
    private static final long serialVersionUID = 3864869408888674716L;
    private final StatusAckPacket.StatusAckHeader header;
    private final Packet payload;

    private StatusAckPacket(byte[] rawData, int offset, int length) throws IllegalRawDataException {
        this.header = new StatusAckPacket.StatusAckHeader(rawData, offset, length);
        int payloadLength = length - header.length();
        int payloadOffset = offset + header.length();
        if(payloadLength > 0){
            this.payload = (Packet) PacketFactories.getFactory(Packet.class, NotApplicable.class).newInstance(rawData, payloadOffset, payloadLength, new NotApplicable[]{NotApplicable.UNKNOWN});
        }else{
            this.payload = null;
        }
    }

    private StatusAckPacket(StatusAckPacket.Builder builder) {
        this.header = new StatusAckPacket.StatusAckHeader(builder);
        this.payload = builder.payloadBuilder != null ? builder.payloadBuilder.build() : null;
    }

    public static StatusAckPacket newPacket(byte[] rawData, int offset, int length)
            throws IllegalRawDataException {
        ByteArrays.validateBounds(rawData, offset, length);
        return new StatusAckPacket(rawData, offset, length);
    }

    @Override
    public StatusAckPacket.StatusAckHeader getHeader() {
        return header;
    }

    @Override
    public Packet getPayload() {
        return payload;
    }

    @Override
    public StatusAckPacket.Builder getBuilder() {
        return new StatusAckPacket.Builder(this);
    }

    /** Builder*/
    public static final class Builder extends AbstractBuilder {
        private StatusAckPacketAuthorityCode authorityCode;
        private Uuid srcUuid;
        private Uuid groupUuid;
        private Packet.Builder payloadBuilder;

        /** */
        public Builder() {}

        private Builder(StatusAckPacket packet) {
            this.authorityCode = packet.header.authorityCode;
            this.srcUuid = packet.header.srcUuid;
            this.groupUuid = packet.header.groupUuid;
            this.payloadBuilder = packet.payload != null ? packet.payload.getBuilder() : null;
        }

        public StatusAckPacket.Builder authorityCode(StatusAckPacketAuthorityCode authorityCode) {
            this.authorityCode = authorityCode;
            return this;
        }

        public StatusAckPacket.Builder srcUuid(Uuid srcUuid) {
            this.srcUuid = srcUuid;
            return this;
        }

        public StatusAckPacket.Builder groupUuid(Uuid groupUuid) {
            this.groupUuid = groupUuid;
            return this;
        }

        @Override
        public StatusAckPacket.Builder payloadBuilder(Packet.Builder payloadBuilder) {
            this.payloadBuilder = payloadBuilder;
            return this;
        }

        @Override
        public Packet.Builder getPayloadBuilder() {
            return payloadBuilder;
        }

        @Override
        public StatusAckPacket build() {
            return new StatusAckPacket(this);
        }
    }

    /** Header*/
    public static final class StatusAckHeader extends AbstractHeader{

        @Serial
        private static final long serialVersionUID = 1010716698852108088L;
        private static final int AUTHORITY_OFFSET = 0;
        private static final int AUTHORITY_SIZE = BYTE_SIZE_IN_BYTES;
        private static final int SRC_UUID_OFFSET = AUTHORITY_OFFSET + AUTHORITY_SIZE;
        private static final int SRC_UUID_SIZE = 32;
        private static final int GROUP_UUID_OFFSET = SRC_UUID_OFFSET + SRC_UUID_SIZE;
        private static final int GROUP_UUID_SIZE = 32;

        private static final int STATUS_ACK_HEADER_SIZE = GROUP_UUID_OFFSET + GROUP_UUID_SIZE;

        private final StatusAckPacketAuthorityCode authorityCode;
        private final Uuid srcUuid;
        private final Uuid groupUuid;

        private StatusAckHeader(byte[] rawData, int offset, int length) throws IllegalRawDataException {
            if (length < STATUS_ACK_HEADER_SIZE) {
                String sb = "The data is too short to build an StatusAck header(" +
                        STATUS_ACK_HEADER_SIZE +
                        " bytes). data: " +
                        ByteArrays.toHexString(rawData, " ") +
                        ", offset: " +
                        offset +
                        ", length: " +
                        length;
                throw new IllegalRawDataException(sb);
            }
            this.authorityCode = StatusAckPacketAuthorityCode.getInstance(ByteArrays.getByte(rawData, AUTHORITY_OFFSET + offset));
            this.srcUuid = Uuid.getInstance(ByteArrays.getSubArray(rawData, SRC_UUID_OFFSET + offset, SRC_UUID_SIZE));
            this.groupUuid = Uuid.getInstance(ByteArrays.getSubArray(rawData, GROUP_UUID_OFFSET + offset, GROUP_UUID_SIZE));
        }

        private StatusAckHeader(StatusAckPacket.Builder builder) {
            this.authorityCode = builder.authorityCode;
            this.srcUuid = builder.srcUuid;
            this.groupUuid = builder.groupUuid;
        }

        @Override
        protected List<byte[]> getRawFields() {
            List<byte[]> rawFields = new ArrayList<>();
            rawFields.add(ByteArrays.toByteArray(authorityCode.value()));
            rawFields.add(srcUuid.toByteArray());
            rawFields.add(groupUuid.toByteArray());
            return rawFields;
        }

        @Override
        protected String buildString() {
            StringBuilder sb = new StringBuilder();
            String ls = System.getProperty("line.separator");

            sb.append("[StatusAckPacket Header (").append(length()).append(" bytes)]").append(ls);
            sb.append("  Status authority code: ").append(authorityCode).append(ls);
            sb.append("  Src uuid: ").append(srcUuid).append(ls);
            sb.append("  Group uuid: ").append(groupUuid).append(ls);

            return sb.toString();
        }

        @Override
        public int length() {
            return STATUS_ACK_HEADER_SIZE;
        }

        public StatusAckPacketAuthorityCode getAuthorityCode() {
            return this.authorityCode;
        }

        public Uuid getSrcUuid() {
            return this.srcUuid;
        }

        public Uuid getGroupUuid() {
            return this.groupUuid;
        }
    }
}
