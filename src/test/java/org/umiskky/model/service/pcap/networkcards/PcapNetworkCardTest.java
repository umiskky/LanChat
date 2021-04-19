package org.umiskky.model.service.pcap.networkcards;

import org.junit.Test;
import org.umiskky.service.pcap.networkcards.NetworkCard;
import org.umiskky.service.pcap.networkcards.PcapNetworkCard;

import java.util.HashMap;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/19
 */
public class PcapNetworkCardTest {
    @Test
    public void TestPcapNetworkCard(){
        HashMap<String, NetworkCard> allDevs = PcapNetworkCard.getAllNetworkCards();
        System.out.println(allDevs);
    }
}
