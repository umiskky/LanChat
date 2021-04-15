package org.umiskky.model.pcap.packet;

import org.pcap4j.packet.AbstractPacket;

import java.util.List;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/15
 */
public final class MakeFriendsPacket extends AbstractPacket{

    private static final long serialVersionUID = 2861056041731440217L;

    @Override
    public MakeFriendsPacket.Builder getBuilder() {
        return null;
    }


    public static final class Builder extends AbstractPacket.AbstractBuilder {


        @Override
        public MakeFriendsPacket build() {
            return null;
        }
    }

    public static final class MakeFriendsHeader extends AbstractPacket.AbstractHeader {

        @Override
        protected List<byte[]> getRawFields() {
            return null;
        }
    }

}
