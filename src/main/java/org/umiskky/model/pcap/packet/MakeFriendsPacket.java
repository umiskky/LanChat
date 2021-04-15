package org.umiskky.model.pcap.packet;

import lombok.Getter;
import org.pcap4j.packet.AbstractPacket;
import org.pcap4j.packet.IllegalRawDataException;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.factory.PacketFactories;
import org.pcap4j.packet.namednumber.NotApplicable;
import org.pcap4j.util.ByteArrays;
import org.umiskky.model.pcap.util.SymmetricEncryptionKey;
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
public final class MakeFriendsPacket extends AbstractPacket{

    @Serial
    private static final long serialVersionUID = -552277388923227567L;

    private final MakeFriendsPacket.MakeFriendsHeader header;
    private final Packet payload;

    private MakeFriendsPacket(byte[] rawData, int offset, int length) throws IllegalRawDataException {
        this.header = new MakeFriendsPacket.MakeFriendsHeader(rawData, offset, length);
        int payloadLength = length - header.length();
        int payloadOffset = offset + header.length();
        if(payloadLength > 0){
            this.payload = (Packet) PacketFactories.getFactory(Packet.class, NotApplicable.class).newInstance(rawData, payloadOffset, payloadLength, new NotApplicable[]{NotApplicable.UNKNOWN});
        }else{
            this.payload = null;
        }
    }

    private MakeFriendsPacket(MakeFriendsPacket.Builder builder) {
        this.header = new MakeFriendsPacket.MakeFriendsHeader(builder);
        this.payload = builder.payloadBuilder != null ? builder.payloadBuilder.build() : null;
    }

    public static MakeFriendsPacket newPacket(byte[] rawData, int offset, int length)
            throws IllegalRawDataException {
        ByteArrays.validateBounds(rawData, offset, length);
        return new MakeFriendsPacket(rawData, offset, length);
    }

    @Override
    public MakeFriendsPacket.MakeFriendsHeader getHeader() {
        return header;
    }

    @Override
    public Packet getPayload() {
        return payload;
    }

    @Override
    public MakeFriendsPacket.Builder getBuilder() {
        return new MakeFriendsPacket.Builder(this);
    }

    /** Builder*/
    public static final class Builder extends AbstractPacket.AbstractBuilder {
        private Uuid uuid;
        private SymmetricEncryptionKey key;
        private Packet.Builder payloadBuilder;

        /** */
        public Builder() {}

        private Builder(MakeFriendsPacket packet) {
            this.uuid = packet.header.uuid;
            this.key = packet.header.key;
            this.payloadBuilder = packet.payload != null ? packet.payload.getBuilder() : null;
        }

        public MakeFriendsPacket.Builder uuid(Uuid uuid) {
            this.uuid = uuid;
            return this;
        }

        public MakeFriendsPacket.Builder key(SymmetricEncryptionKey key) {
            this.key = key;
            return this;
        }

        @Override
        public MakeFriendsPacket.Builder payloadBuilder(Packet.Builder payloadBuilder) {
            this.payloadBuilder = payloadBuilder;
            return this;
        }

        @Override
        public Packet.Builder getPayloadBuilder() {
            return payloadBuilder;
        }

        @Override
        public MakeFriendsPacket build() {
            return new MakeFriendsPacket(this);
        }
    }

    /** Header*/
    public static final class MakeFriendsHeader extends AbstractPacket.AbstractHeader {

        @Serial
        private static final long serialVersionUID = 2229019981885130871L;
        private static final int SRC_UUID_OFFSET = 0;
        private static final int SRC_UUID_SIZE = 32;
        private static final int KEY_OFFSET = SRC_UUID_OFFSET + SRC_UUID_SIZE;
        private static final int KEY_SIZE = LONG_SIZE_IN_BYTES;

        private static final int MAKE_FRIENDS_HEADER_SIZE = KEY_OFFSET + KEY_SIZE;

        @Getter
        private final Uuid uuid;
        @Getter
        private final SymmetricEncryptionKey key;

        private MakeFriendsHeader(byte[] rawData, int offset, int length) throws IllegalRawDataException {
            if (length < MAKE_FRIENDS_HEADER_SIZE) {
                StringBuilder sb = new StringBuilder(100);
                sb.append("The data is too short to build an MakeFriends header(")
                        .append(MAKE_FRIENDS_HEADER_SIZE)
                        .append(" bytes). data: ")
                        .append(ByteArrays.toHexString(rawData, " "))
                        .append(", offset: ")
                        .append(offset)
                        .append(", length: ")
                        .append(length);
                throw new IllegalRawDataException(sb.toString());
            }
            this.uuid = Uuid.getInstance(ByteArrays.getSubArray(rawData, SRC_UUID_OFFSET + offset, SRC_UUID_SIZE));
            this.key = SymmetricEncryptionKey.getInstance(ByteArrays.getSubArray(rawData, KEY_OFFSET + offset, KEY_SIZE));
        }

        private MakeFriendsHeader(MakeFriendsPacket.Builder builder) {
            this.uuid = builder.uuid;
            this.key = builder.key;
        }

        @Override
        protected List<byte[]> getRawFields() {
            List<byte[]> rawFields = new ArrayList<byte[]>();
            rawFields.add(uuid.toByteArray());
            rawFields.add(key.getKey());
            return rawFields;
        }

        @Override
        protected String buildString() {
            StringBuilder sb = new StringBuilder();
            String ls = System.getProperty("line.separator");

            sb.append("[MakeFriendsPacket Header (").append(length()).append(" bytes)]").append(ls);
            sb.append("  Uuid: ").append(uuid).append(ls);
            sb.append("  Symmetric Encryption Key: ").append(key).append(ls);

            return sb.toString();
        }

        @Override
        public int length() {
            return MAKE_FRIENDS_HEADER_SIZE;
        }
    }

}
