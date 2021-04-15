package org.umiskky.model.pcap.util;

import lombok.Getter;
import org.pcap4j.util.ByteArrays;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/15
 */
public class Uuid implements Serializable {

    private static final long serialVersionUID = 7000124657951990508L;

    @Getter
    private final byte[] uuid;

    protected Uuid(byte[] uuid) {
        this.uuid = uuid;
    }

    /**
     * @description The method getByUuid is used to get Uuid object from byte array.
     * @param uuid
     * @return org.umiskky.model.pcap.util.Uuid
     * @author umiskky
     * @date 2021/4/15-12:49
     */
    public static Uuid getUuidByByte(byte[] uuid) {
        return new Uuid(ByteArrays.clone(uuid));
    }

    /**
     * @description The method getByUuidString is used to get Uuid object from uuid string.
     * @param uuid
     * @return org.pcap4j.util.LinkLayerAddress
     * @author umiskky
     * @date 2021/4/15-12:51
     */
    public static Uuid getUuidByString(String uuid) {
        if(uuid != null && uuid.length() != 0){
            byte[] res = new byte[uuid.length()];
            for(int i=0; i<uuid.length(); i++){
                res[i] = (byte) uuid.toCharArray()[i];
            }
            return getUuidByByte(res);
        }
        return null;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.uuid);
    }
}
