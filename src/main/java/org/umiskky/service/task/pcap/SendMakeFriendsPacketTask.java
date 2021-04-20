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
import org.umiskky.service.pcaplib.packet.MakeFriendsPacket;
import org.umiskky.service.pcaplib.packet.domain.SymmetricEncryptionKey;
import org.umiskky.service.pcaplib.packet.domain.Uuid;
import org.umiskky.service.pcaplib.pnif.SendNifBuilder;
import org.umiskky.service.task.InitTask;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/20
 */
public class SendMakeFriendsPacketTask implements Runnable{
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SendMakeFriendsPacketTask.class);
    private final NetworkCard networkCard;
    private final MacAddress dstMacAddress;

    public SendMakeFriendsPacketTask(NetworkCard networkCard, MacAddress dstMacAddress) {
        this.networkCard = networkCard;
        this.dstMacAddress = dstMacAddress;
    }

    @Override
    public void run() {
        PcapHandle handle = new SendNifBuilder(networkCard.getName()).build();
        LocalUser localUser = InitTask.localUser;

        MakeFriendsPacket.Builder makeFriendsBuilder = new MakeFriendsPacket.Builder();
        makeFriendsBuilder
                .uuid(Uuid.getInstance(localUser.getUuid()))
                .key(SymmetricEncryptionKey.getInstance(localUser.getKey()));

        EthernetPacket.Builder etherBuilder = new EthernetPacket.Builder();
        etherBuilder
                .dstAddr(dstMacAddress)
                .srcAddr(MacAddress.getByName(networkCard.getLinkLayerAddr()))
                .type(EtherType.getInstance((short) 0xAAA1))
                .payloadBuilder(makeFriendsBuilder)
                .paddingAtBuild(true);

        Packet packet = etherBuilder.build();

        try {
            handle.sendPacket(packet);
            log.debug("Send Make Friends Packet: " + packet.toString());
        } catch (PcapNativeException | NotOpenException e) {
            log.error(e.getMessage());
        }
    }
}
