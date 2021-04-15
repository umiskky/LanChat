package org.umiskky.model.pcap.util;

import lombok.Getter;

import java.io.Serializable;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/15
 */
public class AvatarId implements Serializable {
    private static final long serialVersionUID = 2969123136096673382L;

    @Getter
    private final byte avatarId;

    protected AvatarId(byte avatarId) {
        this.avatarId = avatarId;
    }

    public static AvatarId getAvatarIdByByte(byte avatarId) {
        return new AvatarId(avatarId);
    }

    public static AvatarId getAvatarIdByInt(Integer avatarId) {
        return new AvatarId(avatarId.byteValue());
    }

    public  Integer toInt(){
        return Integer.parseInt(String.valueOf(this.avatarId));
    }

}
