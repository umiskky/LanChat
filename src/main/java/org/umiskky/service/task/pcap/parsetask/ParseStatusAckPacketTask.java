package org.umiskky.service.task.pcap.parsetask;

import org.pcap4j.packet.EthernetPacket;
import org.slf4j.Logger;
import org.umiskky.model.dao.ApplyDAO;
import org.umiskky.model.dao.FriendDAO;
import org.umiskky.model.dao.GroupDAO;
import org.umiskky.model.dao.UserDAO;
import org.umiskky.model.entity.*;
import org.umiskky.service.pcaplib.packet.StatusAckPacket;
import org.umiskky.service.pcaplib.packet.namednumber.StatusAckPacketAuthorityCode;

import java.time.Instant;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/21
 */
public class ParseStatusAckPacketTask implements Runnable{

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ParseStatusAckPacketTask.class);
    private final EthernetPacket ethernetPacket;
    private final StatusAckPacket packet;

    public ParseStatusAckPacketTask(EthernetPacket ethernetPacket) {
        this.ethernetPacket = ethernetPacket;
        this.packet = (StatusAckPacket) ethernetPacket.getPayload();
    }

    @Override
    public void run() {
        Thread.currentThread().setName(Thread.currentThread().getName() + "(Parse_StatusAckPacket_Thread)");
        StatusAckPacketAuthorityCode authorityCode = packet.getHeader().getAuthorityCode();

        if(StatusAckPacketAuthorityCode.FRIEND_SUCCESS.equals(authorityCode)){

            Friend friend = new Friend();

            Apply apply = new Apply();
            apply.setUuid(packet.getHeader().getSrcUuid().getUuid());
            apply.setGroupUuid(packet.getHeader().getGroupUuid().getUuid());
            apply.setIsGroup(Boolean.FALSE);

            friend.setKey(ApplyDAO.getApply(apply).getKey());
            ApplyDAO.removeApply(apply);

            User user = UserDAO.getUserById(packet.getHeader().getSrcUuid().getUuid());
            if(FriendDAO.getFriendById(user.getUuid()) == null){
                friend.setUuid(user.getUuid());
                friend.setNickname(user.getNickname());
                friend.setAvatarId(user.getAvatarId());
                friend.setIpAddress(user.getIpAddress());
                friend.setServerPort(user.getServerPort());
                friend.setStatus(Boolean.TRUE);
                friend.setLastUpdated(Instant.now().toEpochMilli());
                FriendDAO.putFriend(friend);

                log.debug("Make friends success.\n" + packet);
            }else{
                log.info("Already be friend.\n" + FriendDAO.getFriendById(user.getUuid()));
            }

        }else if(StatusAckPacketAuthorityCode.GROUP_SUCCESS.equals(authorityCode)){
            Group group = GroupDAO.getGroupById(packet.getHeader().getGroupUuid().getUuid());
            if(group != null){
                Apply apply = new Apply();
                apply.setUuid(packet.getHeader().getSrcUuid().getUuid());
                apply.setGroupUuid(packet.getHeader().getGroupUuid().getUuid());
                apply.setIsGroup(Boolean.TRUE);
                ApplyDAO.removeApply(apply);

                User user = UserDAO.getUserById(packet.getHeader().getSrcUuid().getUuid());

                GroupMember groupMember = new GroupMember();
                groupMember.setUuid(user.getUuid());
                groupMember.setNickname(user.getNickname());
                groupMember.setAvatarId(user.getAvatarId());
                groupMember.setIpAddress(user.getIpAddress());
                groupMember.setServerPort(user.getServerPort());
                groupMember.setStatus(Boolean.TRUE);
                groupMember.setLastUpdated(Instant.now().toEpochMilli());

                group.groupMembers.add(groupMember);
                GroupDAO.putGroup(group);
                log.debug("Invite to group success.\n" + packet);
            }
//            log.debug("Invite To Group Success:\n"
//                    + "Group Member: " + groupMember
//                    + "\nGroup: " + group);
            log.debug("Invalid packet.");

        }else if(StatusAckPacketAuthorityCode.FRIEND_REJECT.equals(authorityCode)){

            Apply apply = new Apply();
            apply.setUuid(packet.getHeader().getSrcUuid().getUuid());
            apply.setGroupUuid(packet.getHeader().getGroupUuid().getUuid());
            apply.setIsGroup(Boolean.FALSE);
            ApplyDAO.removeApply(apply);

            log.debug("Refused to be friends.\n" + packet);

        }else if(StatusAckPacketAuthorityCode.GROUP_REJECT.equals(authorityCode)){

            Apply apply = new Apply();
            apply.setUuid(packet.getHeader().getSrcUuid().getUuid());
            apply.setGroupUuid(packet.getHeader().getGroupUuid().getUuid());
            apply.setIsGroup(Boolean.TRUE);
            ApplyDAO.removeApply(apply);

            log.debug("Refused to be group member.\n" + packet);
        }
    }
}
