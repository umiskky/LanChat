package org.umiskky.service.pcaplib.packet.namednumber;

import org.pcap4j.packet.namednumber.NamedNumber;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/15
 */
public class StatusAckPacketAuthorityCode extends NamedNumber<Byte, StatusAckPacketAuthorityCode> {

    public static final StatusAckPacketAuthorityCode FRIEND_SUCCESS
            = new StatusAckPacketAuthorityCode((byte)0, "FriendSuccess");

    public static final StatusAckPacketAuthorityCode GROUP_SUCCESS
            = new StatusAckPacketAuthorityCode((byte)1, "GroupSuccess");

    public static final StatusAckPacketAuthorityCode FRIEND_REJECT
            = new StatusAckPacketAuthorityCode((byte)2, "FriendReject");

    public static final StatusAckPacketAuthorityCode GROUP_REJECT
            = new StatusAckPacketAuthorityCode((byte)3, "GroupReject");

    @Serial
    private static final long serialVersionUID = 281628009168287561L;
    private static final Map<Byte, StatusAckPacketAuthorityCode> registry
            = new HashMap<>();

    static {
        registry.put(FRIEND_SUCCESS.value(), FRIEND_SUCCESS);
        registry.put(GROUP_SUCCESS.value(), GROUP_SUCCESS);
        registry.put(FRIEND_REJECT.value(), FRIEND_REJECT);
        registry.put(GROUP_REJECT.value(), GROUP_REJECT);
    }

    public StatusAckPacketAuthorityCode(Byte value, String name) {
        super(value, name);
    }

    public static StatusAckPacketAuthorityCode getInstance(Byte value) {
        if (registry.containsKey(value)) {
            return registry.get(value);
        }
        else {
            return new StatusAckPacketAuthorityCode(value, "unknown");
        }
    }

    public static StatusAckPacketAuthorityCode register(StatusAckPacketAuthorityCode version) {
        return registry.put(version.value(), version);
    }

    @Override
    public int compareTo(StatusAckPacketAuthorityCode o) {
        return value().compareTo(o.value());
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
