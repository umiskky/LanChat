package org.umiskky.service.task.pcap.parsetask;

import org.pcap4j.packet.EthernetPacket;
import org.slf4j.Logger;
import org.umiskky.factories.ServiceDispatcher;
import org.umiskky.model.dao.FriendDAO;
import org.umiskky.model.dao.UserDAO;
import org.umiskky.model.entity.Friend;
import org.umiskky.model.entity.User;
import org.umiskky.service.pcaplib.networkcards.NetworkCard;
import org.umiskky.service.pcaplib.packet.MakeFriendsPacket;
import org.umiskky.service.pcaplib.packet.namednumber.HelloPacketTypeCode;
import org.umiskky.service.task.InitTask;
import org.umiskky.service.task.pcap.sendtask.SendHelloPacketTask;

import java.time.Instant;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/21
 * ToDo 增加数据库字段以存储等待回复的列表
 */
public class ParseMakeFriendsPacketTask implements Runnable{

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ParseMakeFriendsPacketTask.class);

    private final EthernetPacket ethernetPacket;
    private final MakeFriendsPacket packet;
    private final NetworkCard networkCard;

    public ParseMakeFriendsPacketTask(EthernetPacket ethernetPacket, NetworkCard networkCard) {
        this.ethernetPacket = ethernetPacket;
        this.packet = (MakeFriendsPacket) ethernetPacket.getPayload();
        this.networkCard = networkCard;
    }

    public ParseMakeFriendsPacketTask(EthernetPacket ethernetPacket) {
        this.ethernetPacket = ethernetPacket;
        this.packet = (MakeFriendsPacket) ethernetPacket.getPayload();
        this.networkCard = InitTask.networkCardSelected;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(Thread.currentThread().getName() + "(Parse_MakeFriendsPacket_Thread)");
        User user = UserDAO.getUserById(packet.getHeader().getUuid().getUuid());
        if(user == null){
            SendHelloPacketTask sendHelloPacketTask = new SendHelloPacketTask(networkCard, ethernetPacket.getHeader().getSrcAddr(), HelloPacketTypeCode.HELLOREQUEST);
            ServiceDispatcher.submitTask(sendHelloPacketTask);
            log.debug("Miss user info!\n" + packet);
        }else{
            Friend friend = new Friend();
            friend.setUuid(user.getUuid());
            friend.setNickname(user.getNickname());
            friend.setAvatarId(user.getAvatarId());
            friend.setIpAddress(user.getIpAddress());
            friend.setServerPort(user.getServerPort());
            friend.setStatus(Boolean.TRUE);
            friend.setLastUpdated(Instant.now().toEpochMilli());
            friend.setKey(packet.getHeader().getKey().getKey());
            FriendDAO.putFriend(friend);
            log.debug("Add friend.\n" + packet);
        }
    }
}
