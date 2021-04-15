package org.umiskky.model.pcap.packet;

import org.pcap4j.packet.AbstractPacket;

import java.util.List;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/15
 */
public final class StatusAckPacket extends AbstractPacket {

    private static final long serialVersionUID = 3864869408888674716L;

    @Override
    public Builder getBuilder() {
        return null;
    }


    public static final class Builder extends AbstractBuilder {


        @Override
        public StatusAckPacket build() {
            return null;
        }
    }

    public static final class StatusAckHeader extends AbstractHeader{

        @Override
        protected List<byte[]> getRawFields() {
            return null;
        }
    }
}
