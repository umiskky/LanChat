package org.umiskky.service.pcap.packet;

import lombok.Getter;
import org.pcap4j.packet.AbstractPacket;
import org.pcap4j.packet.IllegalRawDataException;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.factory.PacketFactories;
import org.pcap4j.packet.namednumber.NotApplicable;
import org.pcap4j.packet.namednumber.TcpPort;
import org.pcap4j.util.ByteArrays;
import org.umiskky.service.pcap.packet.domain.AvatarId;
import org.umiskky.service.pcap.packet.domain.Uuid;
import org.umiskky.service.pcap.packet.namednumber.HelloPacketTypeCode;

import java.io.Serial;
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

    @Serial
    private static final long serialVersionUID = 8098262229970686414L;

    private final HelloPacket.HelloHeader header;
    private final Packet payload;

    private HelloPacket(byte[] rawData, int offset, int length) throws IllegalRawDataException {
        this.header = new HelloPacket.HelloHeader(rawData, offset, length);
        int payloadLength = length - header.length();
        int payloadOffset = offset + header.length();
        if(payloadLength > 0){
            this.payload = (Packet)PacketFactories.getFactory(Packet.class, NotApplicable.class).newInstance(rawData, payloadOffset, payloadLength, new NotApplicable[]{NotApplicable.UNKNOWN});
        }else{
            this.payload = null;
        }
    }

    private HelloPacket(HelloPacket.Builder builder) {
        this.header = new HelloPacket.HelloHeader(builder);
        this.payload = builder.payloadBuilder != null ? builder.payloadBuilder.build() : null;
    }

    public static HelloPacket newPacket(byte[] rawData, int offset, int length)
            throws IllegalRawDataException {
        ByteArrays.validateBounds(rawData, offset, length);
        return new HelloPacket(rawData, offset, length);
    }

    @Override
    public HelloPacket.HelloHeader getHeader() {
        return header;
    }

    @Override
    public Packet getPayload() {
        return payload;
    }

    @Override
    public HelloPacket.Builder getBuilder() {
        return new HelloPacket.Builder(this);
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

        public HelloPacket.Builder typeCode(HelloPacketTypeCode typeCode) {
            this.typeCode = typeCode;
            return this;
        }

        public HelloPacket.Builder uuid(Uuid uuid) {
            this.uuid = uuid;
            return this;
        }

        public HelloPacket.Builder serverAddress(Inet4Address serverAddress) {
            this.serverAddress = serverAddress;
            return this;
        }

        public HelloPacket.Builder serverPort(TcpPort serverPort) {
            this.serverPort = serverPort;
            return this;
        }

        public HelloPacket.Builder avatarId(AvatarId avatarId) {
            this.avatarId = avatarId;
            return this;
        }

        @Override
        public HelloPacket.Builder payloadBuilder(Packet.Builder payloadBuilder) {
            this.payloadBuilder = payloadBuilder;
            return this;
        }

        @Override
        public Packet.Builder getPayloadBuilder() {
            return payloadBuilder;
        }

        @Override
        public HelloPacket build() {
            return new HelloPacket(this);
        }
    }

    /** Header*/
    public static final class HelloHeader extends AbstractHeader{

        @Serial
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
                String sb = "The data is too short to build an Hello header(" +
                        HELLO_HEADER_SIZE +
                        " bytes). data: " +
                        ByteArrays.toHexString(rawData, " ") +
                        ", offset: " +
                        offset +
                        ", length: " +
                        length;
                throw new IllegalRawDataException(sb);
            }

            this.typeCode = HelloPacketTypeCode.getInstance(ByteArrays.getByte(rawData, TYPE_OFFSET + offset));
            this.uuid = Uuid.getInstance(ByteArrays.getSubArray(rawData, SRC_UUID_OFFSET + offset, SRC_UUID_SIZE));
            this.serverAddress = ByteArrays.getInet4Address(rawData, IP_ADDR_OFFSET + offset);
            this.serverPort = TcpPort.getInstance(ByteArrays.getShort(rawData, SOCKET_PORT_OFFSET + offset));
            this.avatarId = AvatarId.getInstance(ByteArrays.getByte(rawData, AVATAR_ID_OFFSET + offset));
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
            List<byte[]> rawFields = new ArrayList<>();
            rawFields.add(ByteArrays.toByteArray(typeCode.value()));
            rawFields.add(uuid.toByteArray());
            rawFields.add(ByteArrays.toByteArray(serverAddress));
            rawFields.add(ByteArrays.toByteArray(serverPort.value()));
            rawFields.add(ByteArrays.toByteArray(avatarId.getAvatarId()));
            return rawFields;
        }

        @Override
        protected String buildString() {
            StringBuilder sb = new StringBuilder();
            String ls = System.getProperty("line.separator");

            sb.append("[HelloPacket Header (").append(length()).append(" bytes)]").append(ls);
            sb.append("  Type: ").append(typeCode).append(ls);
            sb.append("  Uuid: ").append(uuid).append(ls);
            sb.append("  Server address: ").append(serverAddress).append(ls);
            sb.append("  Server port: ").append(serverPort).append(ls);
            sb.append("  Avatar id: ").append(avatarId).append(ls);

            return sb.toString();
        }

        @Override
        public int length() {
            return HELLO_HEADER_SIZE;
        }
    }
}
