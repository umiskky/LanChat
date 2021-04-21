package org.umiskky.service.task.pcap;

import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.namednumber.EtherType;
import org.slf4j.Logger;
import org.umiskky.factories.ServiceDispatcher;
import org.umiskky.service.pcaplib.networkcards.NetworkCard;
import org.umiskky.service.task.InitTask;
import org.umiskky.service.task.pcap.parsetask.*;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/21
 */
public class PacketParseDispatcher implements Runnable{

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(PacketParseDispatcher.class);
    private final EthernetPacket ethernetPacket;
    private final NetworkCard networkCard;

    public PacketParseDispatcher(EthernetPacket ethernetPacket, NetworkCard networkCard) {
        this.ethernetPacket = ethernetPacket;
        this.networkCard = networkCard;
    }

    public PacketParseDispatcher(EthernetPacket ethernetPacket) {
        this.ethernetPacket = ethernetPacket;
        this.networkCard = InitTask.networkCardSelected;
    }

    @Override
    public void run() {
        EtherType etherType = ethernetPacket.getHeader().getType();
        if(etherType.equals(EtherType.getInstance((short) 0xAAA0))){
            ParseHelloPacketTask parseHelloPacketTask = new ParseHelloPacketTask(ethernetPacket, networkCard);
            ServiceDispatcher.submitTask(parseHelloPacketTask);
            log.debug("Dispatch to parseHelloPacketTask.");
        }else if(etherType.equals(EtherType.getInstance((short) 0xAAA1))){
            ParseMakeFriendsPacketTask parseMakeFriendsPacketTask = new ParseMakeFriendsPacketTask(ethernetPacket, networkCard);
            ServiceDispatcher.submitTask(parseMakeFriendsPacketTask);
            log.debug("Dispatch to parseMakeFriendsPacketTask.");
        }else if(etherType.equals(EtherType.getInstance((short) 0xAAA2))){
            ParseStatusAckPacketTask parseStatusAckPacketTask = new ParseStatusAckPacketTask(ethernetPacket);
            ServiceDispatcher.submitTask(parseStatusAckPacketTask);
            log.debug("Dispatch to parseStatusAckPacketTask.");
        }else if(etherType.equals(EtherType.getInstance((short) 0xAAA3))){
            ParseInviteToGroupPacketTask parseInviteToGroupPacketTask = new ParseInviteToGroupPacketTask(ethernetPacket, networkCard);
            ServiceDispatcher.submitTask(parseInviteToGroupPacketTask);
            log.debug("Dispatch to parseInviteToGroupPacketTask.");
        }else if(etherType.equals(EtherType.getInstance((short) 0xAAA4))){
            ParseNotifyPacketTask parseNotifyPacketTask = new ParseNotifyPacketTask(ethernetPacket, networkCard);
            ServiceDispatcher.submitTask(parseNotifyPacketTask);
            log.debug("Dispatch to parseNotifyPacketTask.");
        }
    }
}
