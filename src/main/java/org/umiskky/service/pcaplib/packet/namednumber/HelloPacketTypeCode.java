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
public class HelloPacketTypeCode extends NamedNumber<Byte, HelloPacketTypeCode> {

    public static final HelloPacketTypeCode HELLO
            = new HelloPacketTypeCode((byte)0, "Hello");
    public static final HelloPacketTypeCode HELLOACK
            = new HelloPacketTypeCode((byte)1, "HelloAck");
    public static final HelloPacketTypeCode HELLOREQUEST
            = new HelloPacketTypeCode((byte)2, "HelloRequest");
    public static final HelloPacketTypeCode HELLOREPLY
            = new HelloPacketTypeCode((byte)3, "HelloReply");

    @Serial
    private static final long serialVersionUID = -6536822926297795147L;
    private static final Map<Byte, HelloPacketTypeCode> registry
            = new HashMap<>();

    static {
        registry.put(HELLO.value(), HELLO);
        registry.put(HELLOACK.value(), HELLOACK);
        registry.put(HELLOREQUEST.value(), HELLOREQUEST);
        registry.put(HELLOREPLY.value(), HELLOREPLY);
    }

    public HelloPacketTypeCode(Byte value, String name) {
        super(value, name);
    }

    public static HelloPacketTypeCode getInstance(Byte value) {
        if (registry.containsKey(value)) {
            return registry.get(value);
        }
        else {
            return new HelloPacketTypeCode(value, "unknown");
        }
    }

    public static HelloPacketTypeCode register(HelloPacketTypeCode version) {
        return registry.put(version.value(), version);
    }

    @Override
    public int compareTo(HelloPacketTypeCode o) {
        return value().compareTo(o.value());
    }

    @Override
    public String toString() {
        return super.toString();
    }
}