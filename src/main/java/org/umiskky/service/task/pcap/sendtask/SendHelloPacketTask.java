package org.umiskky.service.task.pcap.sendtask;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.UnknownPacket;
import org.pcap4j.packet.namednumber.EtherType;
import org.pcap4j.packet.namednumber.TcpPort;
import org.pcap4j.util.MacAddress;
import org.slf4j.Logger;
import org.umiskky.model.entity.LocalUser;
import org.umiskky.service.pcaplib.networkcards.NetworkCard;
import org.umiskky.service.pcaplib.packet.HelloPacket;
import org.umiskky.service.pcaplib.packet.domain.AvatarId;
import org.umiskky.service.pcaplib.packet.domain.Uuid;
import org.umiskky.service.pcaplib.packet.namednumber.HelloPacketTypeCode;
import org.umiskky.service.pcaplib.pnif.SendNifBuilder;
import org.umiskky.service.task.InitTask;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/20
 */
public class SendHelloPacketTask implements Runnable {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SendHelloPacketTask.class);
    private final NetworkCard networkCard;
    private final MacAddress dstMacAddress;
    private final HelloPacketTypeCode helloPacketTypeCode;

    public SendHelloPacketTask() {
        this.networkCard = InitTask.networkCardSelected;
        this.dstMacAddress = MacAddress.ETHER_BROADCAST_ADDRESS;
        this.helloPacketTypeCode = HelloPacketTypeCode.HELLO;
    }

    public SendHelloPacketTask(NetworkCard networkCard) {
        this.networkCard = networkCard;
        this.dstMacAddress = MacAddress.ETHER_BROADCAST_ADDRESS;
        this.helloPacketTypeCode = HelloPacketTypeCode.HELLO;
    }

    public SendHelloPacketTask(NetworkCard networkCard, MacAddress dstMacAddress, HelloPacketTypeCode helloPacketTypeCode) {
        this.networkCard = networkCard;
        this.dstMacAddress = dstMacAddress;
        this.helloPacketTypeCode = helloPacketTypeCode;
    }

    public SendHelloPacketTask(MacAddress dstMacAddress, HelloPacketTypeCode helloPacketTypeCode) {
        this.networkCard = InitTask.networkCardSelected;
        this.dstMacAddress = dstMacAddress;
        this.helloPacketTypeCode = helloPacketTypeCode;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(Thread.currentThread().getName() + "(Send_HelloPacket_Thread)");
        PcapHandle handle = new SendNifBuilder(networkCard.getName()).build();
        LocalUser localUser = InitTask.localUser;

        JSONArray payloadJson = new JSONArray();
        JSONObject nickname = new JSONObject();
        nickname.put("nickname", localUser.getNickname()==null ? "" : localUser.getNickname());
        payloadJson.add(nickname);
        String payload = payloadJson.toJSONString();

        HelloPacket.Builder helloBuilder = new HelloPacket.Builder();
        try {
                helloBuilder
                        .avatarId(AvatarId.getInstance(localUser.getAvatarId()))
//                        .serverAddress((Inet4Address) InetAddress.getByName(localUser.getIpAddress().replace("/","")))
                        .serverAddress((Inet4Address) InetAddress.getByName(networkCard.getAddress().replace("/","")))
                        .serverPort(TcpPort.getInstance((short)localUser.getServerPort()))
                        .uuid(Uuid.getInstance(localUser.getUuid()))
                        .typeCode(helloPacketTypeCode)
                        .payloadBuilder(new UnknownPacket.Builder().rawData(payload.getBytes(StandardCharsets.UTF_8)));
        } catch (UnknownHostException e) {
            log.error(e.getMessage());
        }

        EthernetPacket.Builder etherBuilder = new EthernetPacket.Builder();
        etherBuilder
                .dstAddr(dstMacAddress)
                .srcAddr(MacAddress.getByName(networkCard.getLinkLayerAddr()))
                .type(EtherType.getInstance((short) 0xAAA0))
                .payloadBuilder(helloBuilder)
                .paddingAtBuild(true);

        Packet packet = etherBuilder.build();

        try {
            handle.sendPacket(packet);
            String string;
            if (HelloPacketTypeCode.HELLO.equals(helloPacketTypeCode)) {
                string = "Send Hello Packet: ";
            } else if(HelloPacketTypeCode.HELLOACK.equals(helloPacketTypeCode)){
                string = "Send Hello Ack Packet: ";
            } else if (HelloPacketTypeCode.HELLOREQUEST.equals(helloPacketTypeCode)) {
                string = "Send Hello Query Packet: ";
            } else if (HelloPacketTypeCode.HELLOREPLY.equals(helloPacketTypeCode)) {
                string = "Send Hello Query Ack Packet: ";
            } else {
                string = "Unknown Hello Packet";
            }
            log.debug(string + packet);
        } catch (PcapNativeException | NotOpenException e) {
            log.error(e.getMessage());
        }
    }
}
