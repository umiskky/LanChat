package org.umiskky.model.pcap;

import org.junit.Test;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.UnknownPacket;
import org.pcap4j.packet.namednumber.EtherType;
import org.pcap4j.util.MacAddress;
import org.pcap4j.util.NifSelector;

import java.io.IOException;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/14
 */
public class PcapTest {

    public static void main(String[] args) {
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


        byte[] payload = new byte[1501];
        for (int i = 0; i < payload.length; i++) {
            payload[i] = (byte) i;
        }

        EthernetPacket.Builder etherBuilder = new EthernetPacket.Builder();
        etherBuilder
                .dstAddr(MacAddress.ETHER_BROADCAST_ADDRESS)
                .srcAddr(MacAddress.getByName("AA-AA-AA-AA-AA-AA"))
                .type(EtherType.IPV4) // 帧类型
                .payloadBuilder(new UnknownPacket.Builder().rawData(payload)) // 由于 ARP 请求是包含在帧里的, 故需要做一个 payload
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
    public void testPcap(){

    }
}
