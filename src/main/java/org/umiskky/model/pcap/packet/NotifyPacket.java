package org.umiskky.model.pcap.packet;

import org.pcap4j.packet.AbstractPacket;

import java.util.List;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/15
 */
public class NotifyPacket extends AbstractPacket{

    private static final long serialVersionUID = -8360211549901651033L;

    @Override
    public NotifyPacket.Builder getBuilder() {
        return null;
    }


    public static final class Builder extends AbstractPacket.AbstractBuilder {


        @Override
        public NotifyPacket build() {
            return null;
        }
    }

    public static final class NotifyHeader extends AbstractPacket.AbstractHeader {

        @Override
        protected List<byte[]> getRawFields() {
            return null;
        }
    }
}
