package org.umiskky.service.pcaplib.packet.domain;

import cn.hutool.core.util.StrUtil;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/15
 */
public class Uuid implements Serializable {

    @Serial
    private static final long serialVersionUID = 7000124657951990508L;

    private final String uuid;

    public static Uuid invalidUuid = new Uuid(StrUtil.repeatByLength("F", 32));

    protected Uuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * @description The method getInstance is used to get an object of Class Uuid.
     * @param uuid
     * @return org.umiskky.model.pcap.util.Uuid
     * @author umiskky
     * @date 2021/4/15-22:20
     */
    public static Uuid getInstance(byte[] uuid) {
        return new Uuid(new String(uuid));
    }

    public static Uuid getInstance(String uuid) {
        return new Uuid(uuid);
    }

    /**
     * @description The method toByteArray is used to convert the uuid from string to byte array.
     * @param
     * @return byte[]
     * @author umiskky
     * @date 2021/4/15-22:20
     */
    public byte[] toByteArray(){
        return uuid.getBytes();
    }

    @Override
    public String toString() {
        return "<" + uuid + ">";
    }

    public String getUuid() {
        return this.uuid;
    }
}
