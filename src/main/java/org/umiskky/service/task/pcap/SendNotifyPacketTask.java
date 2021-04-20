package org.umiskky.service.task.pcap;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.namednumber.EtherType;
import org.pcap4j.util.MacAddress;
import org.slf4j.Logger;
import org.umiskky.model.entity.LocalUser;
import org.umiskky.service.pcaplib.networkcards.NetworkCard;
import org.umiskky.service.pcaplib.packet.NotifyPacket;
import org.umiskky.service.pcaplib.packet.domain.Timestamp;
import org.umiskky.service.pcaplib.packet.domain.Uuid;
import org.umiskky.service.pcaplib.pnif.SendNifBuilder;
import org.umiskky.service.task.InitTask;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/20
 */
public class SendNotifyPacketTask implements Runnable{
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SendNotifyPacketTask.class);
    private final NetworkCard networkCard;

    public SendNotifyPacketTask(NetworkCard networkCard) {
        this.networkCard = networkCard;
    }

    @Override
    public void run() {
        PcapHandle handle = new SendNifBuilder(networkCard.getName()).build();
        LocalUser localUser = InitTask.localUser;

        NotifyPacket.Builder notifyPacketBuilder = new NotifyPacket.Builder();
        notifyPacketBuilder
                .uuid(Uuid.getInstance(localUser.getUuid()))
                .timestamp(Timestamp.getInstance());

        EthernetPacket.Builder etherBuilder = new EthernetPacket.Builder();
        etherBuilder
                .dstAddr(MacAddress.ETHER_BROADCAST_ADDRESS)
                .srcAddr(MacAddress.getByName(networkCard.getLinkLayerAddr()))
                .type(EtherType.getInstance((short) 0xAAA4))
                .payloadBuilder(notifyPacketBuilder)
                .paddingAtBuild(true);

        Packet packet = etherBuilder.build();

        try {
            handle.sendPacket(packet);
            log.debug("Send Notify Packet: " + packet.toString());
        } catch (PcapNativeException | NotOpenException e) {
            log.error(e.getMessage());
        }
    }
}
