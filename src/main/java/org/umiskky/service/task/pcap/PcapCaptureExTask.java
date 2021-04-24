package org.umiskky.service.task.pcap;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.Packet;
import org.pcap4j.util.MacAddress;
import org.slf4j.Logger;
import org.umiskky.factories.ServiceDispatcher;
import org.umiskky.service.pcaplib.networkcards.NetworkCard;
import org.umiskky.service.pcaplib.pnif.CaptureNifBuilder;
import org.umiskky.service.task.InitTask;
import org.umiskky.service.task.pcap.sendtask.SendHelloPacketTask;

import java.io.EOFException;
import java.util.concurrent.TimeoutException;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/22
 */
public class PcapCaptureExTask extends Thread{
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(PcapCaptureExTask.class);
    private final NetworkCard networkCard;
    private String filter;
    private PcapHandle handle;

    public PcapCaptureExTask(){
        super("Pcap-Capture-Task");
        this.networkCard = InitTask.networkCardSelected;
    }

    public PcapCaptureExTask(NetworkCard networkCard){
        super("Pcap-Capture-Task");
        this.networkCard = networkCard;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(Thread.currentThread().getName() + "(PcapCaptureEx_Thread)");
        filter = "(((ether proto 0xAAA0 or ether proto 0xAAA2) and (ether dst "
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
        while(true){
            if(isInterrupted()){
                log.info("Thread Pcap Capture Task Interrupted!");
                handle.close();
                break;
            }
            Packet packet = null;
            try {
                packet = handle.getNextPacketEx();
            } catch (PcapNativeException | EOFException | NotOpenException e) {
                log.error(e.getMessage());
            } catch (TimeoutException ignored){}

            if(packet != null && packet.contains(EthernetPacket.class)){
                EthernetPacket ethernetPacket = packet.get(EthernetPacket.class);
                PacketParseDispatcher packetParseDispatcher = new PacketParseDispatcher(ethernetPacket, networkCard);
                ServiceDispatcher.submitTask(packetParseDispatcher);
                log.debug("Capture a valid packet.\n" + ethernetPacket);

                if(InitTask.networkCardSelected == null){
                    NetworkCard networkCard = InitTask.networkCardsMapByLinkLayerAddr.get(ethernetPacket.getHeader().getDstAddr().toString().toUpperCase());
                    if(networkCard != null){
                        InitTask.networkCardSelected = networkCard;
                        ServiceDispatcher.submitTask(new SendHelloPacketTask(networkCard));
                    }
                }
            }else if(packet != null){
                log.debug("Capture an invalid packet.\n" + packet);
            }
        }
    }
}
