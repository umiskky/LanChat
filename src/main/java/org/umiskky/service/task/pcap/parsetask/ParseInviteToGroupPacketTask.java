package org.umiskky.service.task.pcap.parsetask;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.pcap4j.packet.EthernetPacket;
import org.slf4j.Logger;
import org.umiskky.model.dao.GroupDAO;
import org.umiskky.model.dao.UserDAO;
import org.umiskky.model.entity.Group;
import org.umiskky.model.entity.GroupMember;
import org.umiskky.model.entity.User;
import org.umiskky.service.pcaplib.networkcards.NetworkCard;
import org.umiskky.service.pcaplib.packet.InviteToGroupPacket;
import org.umiskky.service.task.InitTask;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/21
 * ToDo 增加数据库字段以存储等待回复的列表+Group相关协议可以预留名字与头像id字段
 */
public class ParseInviteToGroupPacketTask implements Runnable{

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ParseInviteToGroupPacketTask.class);
    private final EthernetPacket ethernetPacket;
    private final InviteToGroupPacket packet;
    private final NetworkCard networkCard;

    public ParseInviteToGroupPacketTask(EthernetPacket ethernetPacket, NetworkCard networkCard) {
        this.ethernetPacket = ethernetPacket;
        this.packet = (InviteToGroupPacket)ethernetPacket.getPayload();
        this.networkCard = networkCard;
    }

    public ParseInviteToGroupPacketTask(EthernetPacket ethernetPacket) {
        this.ethernetPacket = ethernetPacket;
        this.packet = (InviteToGroupPacket)ethernetPacket.getPayload();
        this.networkCard = InitTask.networkCardSelected;
    }

    @Override
    public void run() {
        Group group = GroupDAO.getGroupById(packet.getHeader().getGroupUuid().getUuid());
        if(group == null){
            User user = UserDAO.getUserById(packet.getHeader().getSrcUuid().getUuid());
            group = new Group();
            group.setUuid(packet.getHeader().getGroupUuid().getUuid());
            group.setAvatarId(9);
            group.setKey(packet.getHeader().getKey().getKey());
            group.setName(("".equals(user.getNickname()) || user.getNickname() == null) ? "Group-" + IdUtil.simpleUUID().substring(0,6) : "Group-" + user.getNickname());

            JSONArray jsonArray = JSON.parseArray(new String(packet.getPayload().getRawData(), StandardCharsets.UTF_8));
            ArrayList<GroupMember> groupMembers = new ArrayList<>();
            for (Object o : jsonArray) {
                String uuid = (String) o;
                User tmpUser = UserDAO.getUserById(uuid);
                if (tmpUser != null) {
                    GroupMember groupMember = new GroupMember();
                    groupMember.setUuid(tmpUser.getUuid());
                    groupMember.setNickname(tmpUser.getNickname());
                    groupMember.setAvatarId(tmpUser.getAvatarId());
                    groupMember.setIpAddress(tmpUser.getIpAddress());
                    groupMember.setServerPort(tmpUser.getServerPort());
                    groupMember.setStatus(tmpUser.getStatus());
                    groupMember.setLastUpdated(tmpUser.getLastUpdated());
                    groupMembers.add(groupMember);
                }
            }
            group.groupMembers.addAll(groupMembers);

            GroupDAO.putGroup(group);
            log.debug("Join in the group.\n" + packet);
        }else{
            log.debug("Already in group.\n" + packet);
        }
    }
}
