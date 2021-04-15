package org.umiskky.model.pcap.packet;

import lombok.Getter;
import org.pcap4j.packet.AbstractPacket;
import org.pcap4j.packet.IllegalRawDataException;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.namednumber.TcpPort;
import org.pcap4j.util.ByteArrays;
import org.umiskky.model.pcap.namednumber.HelloPacketTypeCode;
import org.umiskky.model.pcap.util.AvatarId;
import org.umiskky.model.pcap.util.Uuid;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

import static org.pcap4j.util.ByteArrays.*;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/15
 */
public final class HelloPacket extends AbstractPacket {

    private static final long serialVersionUID = 8098262229970686414L;

    private final HelloPacket.HelloHeader header;
    private final Packet payload;

    private HelloPacket(byte[] rawData, int offset, int length) throws IllegalRawDataException {
        this.header = new HelloPacket.HelloHeader(rawData, offset, length);
        this.payload = ;
    }

    private HelloPacket(HelloPacket.Builder builder) {

    }

    public static HelloPacket newPacket(byte[] rawData, int offset, int length)
            throws IllegalRawDataException {
        ByteArrays.validateBounds(rawData, offset, length);
        return new HelloPacket(rawData, offset, length);
    }

    @Override
    public Builder getBuilder() {
        return new Builder(this);
    }



    /** Builder*/
    public static final class Builder extends AbstractBuilder {

        private HelloPacketTypeCode typeCode;
        private Uuid uuid;
        private Inet4Address serverAddress;
        private TcpPort serverPort;
        private AvatarId avatarId;
        private Packet.Builder payloadBuilder;

        /** */
        public Builder() {}

        private Builder(HelloPacket packet) {
            this.typeCode = packet.header.typeCode;
            this.uuid = packet.header.uuid;
            this.serverAddress = packet.header.serverAddress;
            this.serverPort = packet.header.serverPort;
            this.avatarId = packet.header.avatarId;
            this.payloadBuilder = packet.payload != null ? packet.payload.getBuilder() : null;
        }

        /**
         * @description The method typeCode is used to build typeCode
         * @param typeCode
         * @return org.umiskky.model.pcap.packet.HelloPacket.Builder
         * @author umiskky
         * @date 2021/4/15-14:49
         */
        public HelloPacket.Builder typeCode(HelloPacketTypeCode typeCode) {
            this.typeCode = typeCode;
            return this;
        }

        @Override
        public HelloPacket build() {
            return null;
        }
    }

    /** Header*/
    public static final class HelloHeader extends AbstractHeader{

        private static final long serialVersionUID = 1540218131071336544L;
        private static final int TYPE_OFFSET = 0;
        private static final int TYPE_SIZE = BYTE_SIZE_IN_BYTES;
        private static final int SRC_UUID_OFFSET = TYPE_OFFSET + TYPE_SIZE;
        private static final int SRC_UUID_SIZE = 32;
        private static final int IP_ADDR_OFFSET = SRC_UUID_OFFSET + SRC_UUID_SIZE;
        private static final int IP_ADDR_SIZE = INET4_ADDRESS_SIZE_IN_BYTES;
        private static final int SOCKET_PORT_OFFSET = IP_ADDR_OFFSET + IP_ADDR_SIZE;
        private static final int SOCKET_PORT_SIZE = SHORT_SIZE_IN_BYTES;
        private static final int AVATAR_ID_OFFSET = SOCKET_PORT_OFFSET + SOCKET_PORT_SIZE;
        private static final int AVATAR_ID_SIZE = BYTE_SIZE_IN_BYTES;

        private static final int HELLO_HEADER_SIZE = AVATAR_ID_OFFSET + AVATAR_ID_SIZE;

        @Getter
        private final HelloPacketTypeCode typeCode;
        @Getter
        private final Uuid uuid;
        @Getter
        private final Inet4Address serverAddress;
        @Getter
        private final TcpPort serverPort;
        @Getter
        private final AvatarId avatarId;

        private HelloHeader(byte[] rawData, int offset, int length) throws IllegalRawDataException {
            if (length < HELLO_HEADER_SIZE) {
                StringBuilder sb = new StringBuilder(100);
                sb.append("The data is too short to build an Hello header(")
                        .append(HELLO_HEADER_SIZE)
                        .append(" bytes). data: ")
                        .append(ByteArrays.toHexString(rawData, " "))
                        .append(", offset: ")
                        .append(offset)
                        .append(", length: ")
                        .append(length);
                throw new IllegalRawDataException(sb.toString());
            }

            this.typeCode = HelloPacketTypeCode.getInstance(ByteArrays.getByte(rawData, TYPE_OFFSET + offset));
            this.uuid = Uuid.getUuidByByte(ByteArrays.getSubArray(rawData, TYPE_OFFSET + offset, SRC_UUID_SIZE));
            this.serverAddress = ByteArrays.getInet4Address(rawData, IP_ADDR_OFFSET + offset);
            this.serverPort = TcpPort.getInstance(ByteArrays.getShort(rawData, SOCKET_PORT_OFFSET + offset));;
            this.avatarId = AvatarId.getAvatarIdByByte(ByteArrays.getByte(rawData, AVATAR_ID_OFFSET + offset));
        }

        private HelloHeader(HelloPacket.Builder builder) {
            this.typeCode = builder.typeCode;
            this.uuid = builder.uuid;
            this.serverAddress = builder.serverAddress;
            this.serverPort = builder.serverPort;
            this.avatarId = builder.avatarId;
        }

        @Override
        protected List<byte[]> getRawFields() {
            List<byte[]> rawFields = new ArrayList<byte[]>();
            rawFields.add(ByteArrays.toByteArray(typeCode.value()));
            rawFields.add(uuid.getUuid());
            rawFields.add(ByteArrays.toByteArray(serverAddress));
            rawFields.add(ByteArrays.toByteArray(serverPort.value()));
            rawFields.add(ByteArrays.toByteArray(avatarId.getAvatarId()));
            return rawFields;
        }

        @Override
        public int length() {
            return HELLO_HEADER_SIZE;
        }
    }
}
