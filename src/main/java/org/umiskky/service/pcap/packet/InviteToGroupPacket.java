package org.umiskky.service.pcap.packet;

import lombok.Getter;
import org.pcap4j.packet.AbstractPacket;
import org.pcap4j.packet.IllegalRawDataException;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.factory.PacketFactories;
import org.pcap4j.packet.namednumber.NotApplicable;
import org.pcap4j.util.ByteArrays;
import org.umiskky.service.pcap.packet.domain.SymmetricEncryptionKey;
import org.umiskky.service.pcap.packet.domain.Uuid;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

import static org.pcap4j.util.ByteArrays.LONG_SIZE_IN_BYTES;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/15
 */
public final class InviteToGroupPacket extends AbstractPacket{

    @Serial
    private static final long serialVersionUID = 7520078455841482662L;

    private final InviteToGroupPacket.InviteToGroupHeader header;
    private final Packet payload;

    private InviteToGroupPacket(byte[] rawData, int offset, int length) throws IllegalRawDataException {
        this.header = new InviteToGroupPacket.InviteToGroupHeader(rawData, offset, length);
        int payloadLength = length - header.length();
        int payloadOffset = offset + header.length();
        if(payloadLength > 0){
            this.payload = (Packet) PacketFactories.getFactory(Packet.class, NotApplicable.class).newInstance(rawData, payloadOffset, payloadLength, new NotApplicable[]{NotApplicable.UNKNOWN});
        }else{
            this.payload = null;
        }
    }

    private InviteToGroupPacket(InviteToGroupPacket.Builder builder) {
        this.header = new InviteToGroupPacket.InviteToGroupHeader(builder);
        this.payload = builder.payloadBuilder != null ? builder.payloadBuilder.build() : null;
    }

    public static InviteToGroupPacket newPacket(byte[] rawData, int offset, int length)
            throws IllegalRawDataException {
        ByteArrays.validateBounds(rawData, offset, length);
        return new InviteToGroupPacket(rawData, offset, length);
    }

    @Override
    public InviteToGroupPacket.InviteToGroupHeader getHeader() {
        return header;
    }

    @Override
    public Packet getPayload() {
        return payload;
    }

    @Override
    public InviteToGroupPacket.Builder getBuilder() {
        return new InviteToGroupPacket.Builder(this);
    }

    /** Builder*/
    public static final class Builder extends AbstractPacket.AbstractBuilder {
        private Uuid srcUuid;
        private Uuid groupUuid;
        private SymmetricEncryptionKey key;
        private Packet.Builder payloadBuilder;

        /** */
        public Builder() {}

        private Builder(InviteToGroupPacket packet) {
            this.srcUuid = packet.header.srcUuid;
            this.groupUuid = packet.header.groupUuid;
            this.key = packet.header.key;
            this.payloadBuilder = packet.payload != null ? packet.payload.getBuilder() : null;
        }

        public InviteToGroupPacket.Builder srcUuid(Uuid srcUuid) {
            this.srcUuid = srcUuid;
            return this;
        }

        public InviteToGroupPacket.Builder groupUuid(Uuid groupUuid) {
            this.groupUuid = groupUuid;
            return this;
        }

        public InviteToGroupPacket.Builder key(SymmetricEncryptionKey key) {
            this.key = key;
            return this;
        }

        @Override
        public InviteToGroupPacket.Builder payloadBuilder(Packet.Builder payloadBuilder) {
            this.payloadBuilder = payloadBuilder;
            return this;
        }

        @Override
        public Packet.Builder getPayloadBuilder() {
            return payloadBuilder;
        }

        @Override
        public InviteToGroupPacket build() {
            return new InviteToGroupPacket(this);
        }
    }

    /** Header*/
    public static final class InviteToGroupHeader extends AbstractPacket.AbstractHeader {

        @Serial
        private static final long serialVersionUID = -5759473553510283183L;
        private static final int SRC_UUID_OFFSET = 0;
        private static final int SRC_UUID_SIZE = 32;
        private static final int GROUP_UUID_OFFSET = SRC_UUID_OFFSET + SRC_UUID_SIZE;
        private static final int GROUP_UUID_SIZE = 32;
        private static final int KEY_OFFSET = GROUP_UUID_OFFSET + GROUP_UUID_SIZE;
        private static final int KEY_SIZE = LONG_SIZE_IN_BYTES;

        private static final int INVITE_TO_GROUP_HEADER_SIZE = KEY_OFFSET + KEY_SIZE;

        @Getter
        private final Uuid srcUuid;
        @Getter
        private final Uuid groupUuid;
        @Getter
        private final SymmetricEncryptionKey key;

        private InviteToGroupHeader(byte[] rawData, int offset, int length) throws IllegalRawDataException {
            if (length < INVITE_TO_GROUP_HEADER_SIZE) {
                String sb = "The data is too short to build an InviteToGroup header(" +
                        INVITE_TO_GROUP_HEADER_SIZE +
                        " bytes). data: " +
                        ByteArrays.toHexString(rawData, " ") +
                        ", offset: " +
                        offset +
                        ", length: " +
                        length;
                throw new IllegalRawDataException(sb);
            }
            this.srcUuid = Uuid.getInstance(ByteArrays.getSubArray(rawData, SRC_UUID_OFFSET + offset, SRC_UUID_SIZE));
            this.groupUuid = Uuid.getInstance(ByteArrays.getSubArray(rawData, GROUP_UUID_OFFSET + offset, GROUP_UUID_SIZE));
            this.key = SymmetricEncryptionKey.getInstance(ByteArrays.getSubArray(rawData, KEY_OFFSET + offset, KEY_SIZE));
        }

        private InviteToGroupHeader(InviteToGroupPacket.Builder builder) {
            this.srcUuid = builder.srcUuid;
            this.groupUuid = builder.groupUuid;
            this.key = builder.key;
        }

        @Override
        protected List<byte[]> getRawFields() {
            List<byte[]> rawFields = new ArrayList<>();
            rawFields.add(srcUuid.toByteArray());
            rawFields.add(groupUuid.toByteArray());
            rawFields.add(key.getKey());
            return rawFields;
        }

        @Override
        protected String buildString() {
            StringBuilder sb = new StringBuilder();
            String ls = System.getProperty("line.separator");

            sb.append("[InviteToGroupPacket Header (").append(length()).append(" bytes)]").append(ls);
            sb.append("  Src uuid: ").append(srcUuid).append(ls);
            sb.append("  Group uuid: ").append(groupUuid).append(ls);
            sb.append("  Symmetric Encryption Key: ").append(key).append(ls);

            return sb.toString();
        }

        @Override
        public int length() {
            return INVITE_TO_GROUP_HEADER_SIZE;
        }
    }
}
