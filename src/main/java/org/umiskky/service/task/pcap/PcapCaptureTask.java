package org.umiskky.service.task.pcap;

import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.util.MacAddress;
import org.slf4j.Logger;
import org.umiskky.factories.ServiceDispatcher;
import org.umiskky.service.pcaplib.networkcards.NetworkCard;
import org.umiskky.service.pcaplib.pnif.CaptureNifBuilder;
import org.umiskky.service.task.InitTask;
import org.umiskky.service.task.pcap.sendtask.SendHelloPacketTask;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/21
 */
public class PcapCaptureTask{
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(PcapCaptureTask.class);
    private final NetworkCard networkCard;
    private String filter;
    private PcapHandle handle;
    private PacketListener listener;

    public PcapCaptureTask(NetworkCard networkCard) {
        this.networkCard = networkCard;
        initialize();
    }

    public PcapCaptureTask() {
        this.networkCard = InitTask.networkCardSelected;
        initialize();
    }

    private void initialize() {
        this.filter = "(((ether proto 0xAAA0 or ether proto 0xAAA2) and (ether dst "
                + Pcaps.toBpfString(MacAddress.getByName(networkCard.getLinkLayerAddr()))
                + " or ether dst "
                + Pcaps.toBpfString(MacAddress.ETHER_BROADCAST_ADDRESS)
                + ")) or ((ether proto 0xAAA1 or ether proto 0xAAA3) and (ether dst "
                + Pcaps.toBpfString(MacAddress.getByName(networkCard.getLinkLayerAddr()))
                + ")) or ((ether proto 0xAAA4) and (ether dst "
                + Pcaps.toBpfString(MacAddress.ETHER_BROADCAST_ADDRESS)
                + "))) and not (ether src "
                + Pcaps.toBpfString(MacAddress.getByName(networkCard.getLinkLayerAddr()))
                + ")";
        log.info("Filter: " + filter);

        this.handle = new CaptureNifBuilder(networkCard.getName()).build(filter);

        this.listener =
            packet -> {
                if (packet.contains(EthernetPacket.class)) {
                    EthernetPacket ethernetPacket = packet.get(EthernetPacket.class);
                    PacketParseDispatcher packetParseDispatcher = new PacketParseDispatcher(ethernetPacket, networkCard);
                    ServiceDispatcher.submitTask(packetParseDispatcher);
                    log.debug("Capture a valid packet.\n" + ethernetPacket);

                    NetworkCard networkCard = InitTask.networkCardsMapByLinkLayerAddr.get(ethernetPacket.getHeader().getDstAddr().toString().toUpperCase());
                    if(networkCard != null){
                        if(InitTask.networkCardSelected == null){
                            InitTask.networkCardSelected = networkCard;
                        }
                        ServiceDispatcher.submitTask(new SendHelloPacketTask(networkCard));
                    }

                }
                log.debug("Capture an invalid packet.\n" + packet);
            };
    }

    public String getFilter() {
        return filter;
    }

    public PcapHandle getHandle() {
        return handle;
    }

    public PacketListener getListener() {
        return listener;
    }
}
