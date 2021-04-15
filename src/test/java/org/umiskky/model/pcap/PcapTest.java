package org.umiskky.model.pcap;

import cn.hutool.core.util.IdUtil;
import org.junit.Test;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.UnknownPacket;
import org.pcap4j.packet.namednumber.EtherType;
import org.pcap4j.packet.namednumber.TcpPort;
import org.pcap4j.util.MacAddress;
import org.pcap4j.util.NifSelector;
import org.umiskky.model.pcap.namednumber.HelloPacketTypeCode;
import org.umiskky.model.pcap.packet.HelloPacket;
import org.umiskky.model.pcap.util.AvatarId;
import org.umiskky.model.pcap.util.Uuid;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/14
 */
public class PcapTest {

    public static void main(String[] args) throws Exception{
        EtherType etherType = new EtherType((short) 0xAAA0, "HELLO");
        EtherType.register(etherType);


        PcapNetworkInterface nif;
        try {nif = new NifSelector().selectNetworkInterface();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (nif == null) {
            return;
        }
        System.out.println(nif.getName() + "(" + nif.getDescription() + ")");

        byte[] nickname = "umiskky".getBytes();

        HelloPacket.Builder helloBuilder = new HelloPacket.Builder();
        helloBuilder.avatarId(AvatarId.getAvatarIdByInt(1))
                .serverAddress((Inet4Address) InetAddress.getByName("192.168.0.1"))
                .serverPort(TcpPort.HELLO_PORT)
                .uuid(Uuid.getUuidByString(IdUtil.simpleUUID()))
                .typeCode(HelloPacketTypeCode.HELLO)
                .payloadBuilder(new UnknownPacket.Builder().rawData(nickname));

        EthernetPacket.Builder etherBuilder = new EthernetPacket.Builder();
        etherBuilder
                .dstAddr(MacAddress.ETHER_BROADCAST_ADDRESS)
                .srcAddr(MacAddress.getByName("AA-AA-AA-AA-AA-AA"))
                .type(EtherType.getInstance((short) 0xAAA0)) // 帧类型
                .payloadBuilder(helloBuilder) // 由于 ARP 请求是包含在帧里的, 故需要做一个 payload
                .paddingAtBuild(true);

        Packet packet = etherBuilder.build();
        PcapHandle sendHandle = null;
        try {
            sendHandle = nif.openLive(65536, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, 10);
        } catch (PcapNativeException e) {
            e.printStackTrace();
        }
        try {
            assert sendHandle != null;
            sendHandle.sendPacket(packet);
        } catch (PcapNativeException | NotOpenException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestPcap(){
        EtherType etherType = new EtherType((short) 0xAAA0, "Hello");
        EtherType.register(etherType);
        System.out.println(EtherType.getInstance((short) 0xAAA0));
    }

}
