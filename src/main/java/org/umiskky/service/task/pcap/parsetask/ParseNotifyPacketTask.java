package org.umiskky.service.task.pcap.parsetask;

import org.pcap4j.packet.EthernetPacket;
import org.slf4j.Logger;
import org.umiskky.factories.ServiceDispatcher;
import org.umiskky.model.dao.FriendDAO;
import org.umiskky.model.dao.GroupMemberDAO;
import org.umiskky.model.dao.UserDAO;
import org.umiskky.service.pcaplib.networkcards.NetworkCard;
import org.umiskky.service.pcaplib.packet.NotifyPacket;
import org.umiskky.service.pcaplib.packet.namednumber.HelloPacketTypeCode;
import org.umiskky.service.task.InitTask;
import org.umiskky.service.task.pcap.sendtask.SendHelloPacketTask;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/21
 */
public class ParseNotifyPacketTask implements Runnable{
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ParseNotifyPacketTask.class);
    private final EthernetPacket ethernetPacket;
    private final NotifyPacket packet;
    private final NetworkCard networkCard;

    public ParseNotifyPacketTask(EthernetPacket ethernetPacket, NetworkCard networkCard) {
        this.ethernetPacket = ethernetPacket;
        this.packet = (NotifyPacket) ethernetPacket.getPayload();
        this.networkCard = networkCard;
    }

    public ParseNotifyPacketTask(EthernetPacket ethernetPacket) {
        this.ethernetPacket = ethernetPacket;
        this.packet = (NotifyPacket) ethernetPacket.getPayload();
        this.networkCard = InitTask.networkCardSelected;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(Thread.currentThread().getName() + "(Parse_NotifyPacket_Thread)");
        String uuid = packet.getHeader().getUuid().getUuid();
        long timestamp = packet.getHeader().getTimestamp().getTimestamp();
        FriendDAO.setStatus(uuid, timestamp);
        log.debug("Update friend status.");
        GroupMemberDAO.setStatus(uuid, timestamp);
        log.debug("Update group member status.");
        int status = UserDAO.setStatus(uuid, timestamp);
        if(status == -1){
            SendHelloPacketTask sendHelloPacketTask = new SendHelloPacketTask(networkCard, ethernetPacket.getHeader().getSrcAddr(), HelloPacketTypeCode.HELLOREQUEST);
            ServiceDispatcher.submitTask(sendHelloPacketTask);
            log.debug("Miss user info!\n" + packet);
        }
        log.debug("Update user status.\n" + packet);
    }
}
