package org.umiskky.service.pcap.packet.domain;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/15
 */
public class AvatarId implements Serializable {
    @Serial
    private static final long serialVersionUID = 2969123136096673382L;

    private final byte avatarId;

    protected AvatarId(byte avatarId) {
        this.avatarId = avatarId;
    }

    /**
     * @description The method getInstance is used to get an object of Class AvatarId.
     * @param avatarId
     * @return org.umiskky.model.pcap.util.AvatarId
     * @author umiskky
     * @date 2021/4/15-22:21
     */
    public static AvatarId getInstance(byte avatarId) {
        return new AvatarId(avatarId);
    }

    public static AvatarId getInstance(int avatarId) {
        return new AvatarId((byte) avatarId);
    }

    @Override
    public String toString() {
        return "avatar-" + (int) avatarId;
    }

    public byte getAvatarId() {
        return this.avatarId;
    }
}
