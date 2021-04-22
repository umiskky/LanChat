package org.umiskky.service.task.pcap.sendtask;

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
import org.umiskky.service.pcaplib.packet.StatusAckPacket;
import org.umiskky.service.pcaplib.packet.domain.Uuid;
import org.umiskky.service.pcaplib.packet.namednumber.StatusAckPacketAuthorityCode;
import org.umiskky.service.pcaplib.pnif.SendNifBuilder;
import org.umiskky.service.task.InitTask;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/20
 */
public class SendStatusAckPacketTask implements Runnable{
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SendStatusAckPacketTask.class);
    private final NetworkCard networkCard;
    private final MacAddress dstMacAddress;
    private final StatusAckPacketAuthorityCode authorityCode;
    private final Uuid groupUuid;

    public SendStatusAckPacketTask(NetworkCard networkCard, MacAddress dstMacAddress, StatusAckPacketAuthorityCode authorityCode) {
        this.networkCard = networkCard;
        this.authorityCode = authorityCode;
        this.dstMacAddress = dstMacAddress;
        this.groupUuid = Uuid.invalidUuid;
    }

    public SendStatusAckPacketTask(NetworkCard networkCard, MacAddress dstMacAddress, StatusAckPacketAuthorityCode authorityCode, Uuid groupUuid) {
        this.networkCard = networkCard;
        this.authorityCode = authorityCode;
        this.dstMacAddress = dstMacAddress;
        this.groupUuid = groupUuid;
    }

    /**
     * @description The method SendStatusAckPacketTask is used to construct a group success packet.
     * @param networkCard
     * @param groupUuid
     * @return
     * @author umiskky
     * @date 2021/4/21-20:38
     */
    public SendStatusAckPacketTask(NetworkCard networkCard, Uuid groupUuid) {
        this.networkCard = networkCard;
        this.authorityCode = StatusAckPacketAuthorityCode.GROUP_SUCCESS;
        this.dstMacAddress = MacAddress.ETHER_BROADCAST_ADDRESS;
        this.groupUuid = groupUuid;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(Thread.currentThread().getName() + "(Send_StatusAckPacket_Thread)");
        PcapHandle handle = new SendNifBuilder(networkCard.getName()).build();
        LocalUser localUser = InitTask.localUser;

        StatusAckPacket.Builder statusAckPacketBuilder = new StatusAckPacket.Builder();
        statusAckPacketBuilder
                .srcUuid(Uuid.getInstance(localUser.getUuid()))
                .authorityCode(authorityCode)
                .groupUuid(groupUuid);

        EthernetPacket.Builder etherBuilder = new EthernetPacket.Builder();
        etherBuilder
                .dstAddr(dstMacAddress)
                .srcAddr(MacAddress.getByName(networkCard.getLinkLayerAddr()))
                .type(EtherType.getInstance((short) 0xAAA2))
                .payloadBuilder(statusAckPacketBuilder)
                .paddingAtBuild(true);

        Packet packet = etherBuilder.build();

        try {
            handle.sendPacket(packet);
            String string;
            if (StatusAckPacketAuthorityCode.FRIEND_SUCCESS.equals(authorityCode)) {
                string = "Send Friend Success Packet: ";
            } else if(StatusAckPacketAuthorityCode.GROUP_SUCCESS.equals(authorityCode)){
                string = "Send Group Success Packet: ";
            } else if (StatusAckPacketAuthorityCode.FRIEND_REJECT.equals(authorityCode)) {
                string = "Send Friend Reject Packet: ";
            } else if (StatusAckPacketAuthorityCode.GROUP_REJECT.equals(authorityCode)) {
                string = "Send Group Reject Packet: ";
            } else {
                string = "Unknown Status Ack Packet";
            }
            log.debug(string + packet.toString());
        } catch (PcapNativeException | NotOpenException e) {
            log.error(e.getMessage());
        }
    }
}
