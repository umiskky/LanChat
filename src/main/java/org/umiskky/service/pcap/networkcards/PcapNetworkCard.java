package org.umiskky.service.pcap.networkcards;

import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;
import org.slf4j.Logger;
import org.umiskky.LanChat;

import java.util.HashMap;
import java.util.List;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/19
 */
public interface PcapNetworkCard {

    Logger log = org.slf4j.LoggerFactory.getLogger(LanChat.class);

    /**
     * @description The method getAllNetworkCards is used to get all network cards and return a HashMap<String, NetworkCard>.
     * @param
     * @return java.util.HashMap<java.lang.String, org.umiskky.service.pcap.networkcards.NetworkCard>
     * @author umiskky
     * @date 2021/4/19-23:14
     */
    public static HashMap<String, NetworkCard> getAllNetworkCards(){
        HashMap<String, NetworkCard> networkCards = new HashMap<>();

        List<PcapNetworkInterface> allDevices;
        try {
            allDevices = Pcaps.findAllDevs();
        } catch (PcapNativeException e) {
            log.error("Pcap Error: " + e);
            return null;
        }

        for(PcapNetworkInterface pnif : allDevices){
            NetworkCard networkCard = new NetworkCard(pnif);
            if(networkCard.isValidNetworkCard()){
                networkCards.put(networkCard.getName(), networkCard);
            }
        }
        return networkCards;
    }
}
