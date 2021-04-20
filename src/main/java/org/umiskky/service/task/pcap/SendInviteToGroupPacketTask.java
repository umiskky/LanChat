package org.umiskky.service.task.pcap;

import com.alibaba.fastjson.JSON;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.UnknownPacket;
import org.pcap4j.packet.namednumber.EtherType;
import org.pcap4j.util.MacAddress;
import org.slf4j.Logger;
import org.umiskky.model.entity.LocalUser;
import org.umiskky.service.pcaplib.networkcards.NetworkCard;
import org.umiskky.service.pcaplib.packet.InviteToGroupPacket;
import org.umiskky.service.pcaplib.packet.domain.SymmetricEncryptionKey;
import org.umiskky.service.pcaplib.packet.domain.Uuid;
import org.umiskky.service.pcaplib.pnif.SendNifBuilder;
import org.umiskky.service.task.InitTask;

import java.util.ArrayList;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/20
 */
public class SendInviteToGroupPacketTask implements Runnable {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SendInviteToGroupPacketTask.class);
    private final NetworkCard networkCard;
    private final MacAddress dstMacAddress;
    private final Uuid groupUuid;
    private final SymmetricEncryptionKey key;
    private final ArrayList<Uuid> groupMembers;

    public SendInviteToGroupPacketTask(NetworkCard networkCard, MacAddress dstMacAddress, Uuid groupUuid, SymmetricEncryptionKey key, ArrayList<Uuid> groupMembers) {
        this.networkCard = networkCard;
        this.dstMacAddress = dstMacAddress;
        this.groupUuid = groupUuid;
        this.key = key;
        this.groupMembers = groupMembers;
    }

    @Override
    public void run() {
        PcapHandle handle = new SendNifBuilder(networkCard.getName()).build();
        LocalUser localUser = InitTask.localUser;

//        Map<String, Object> groupMembersMap = new HashMap<>();
//        groupMembersMap.put("groupMembersNum", groupMembers == null ? 0 : groupMembers.size());
//        groupMembersMap.put("groupMembers", groupMembers == null ? new ArrayList<Uuid>() : groupMembers);
        String payload = JSON.toJSONString(groupMembers);

        InviteToGroupPacket.Builder inviteToGroupPacketBuilder = new InviteToGroupPacket.Builder();

        inviteToGroupPacketBuilder
                .srcUuid(Uuid.getInstance(localUser.getUuid()))
                .groupUuid(groupUuid)
                .key(key)
                .payloadBuilder(new UnknownPacket.Builder().rawData(payload.getBytes()));

        EthernetPacket.Builder etherBuilder = new EthernetPacket.Builder();
        etherBuilder
                .dstAddr(dstMacAddress)
                .srcAddr(MacAddress.getByName(networkCard.getLinkLayerAddr()))
                .type(EtherType.getInstance((short) 0xAAA3))
                .payloadBuilder(inviteToGroupPacketBuilder)
                .paddingAtBuild(true);

        Packet packet = etherBuilder.build();

        try {
            handle.sendPacket(packet);
            log.debug("Send Invite To Group Packet: " + packet.toString());
        } catch (PcapNativeException | NotOpenException e) {
            log.error(e.getMessage());
        }
    }
}
