package org.umiskky.service.pcap.networkcards;

import org.pcap4j.core.PcapAddress;
import org.pcap4j.core.PcapIpV4Address;
import org.pcap4j.core.PcapNetworkInterface;

import java.util.List;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/19
 */
public class NetworkCard {
    private String name;
    private String description;
    private String address;
    private String netmask;
    private String broadcastAddr;
    private String linkLayerAddr;

    public NetworkCard(String name, String description, String address, String netmask, String broadcastAddr, String linkLayerAddr) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.netmask = netmask;
        this.broadcastAddr = broadcastAddr;
        this.linkLayerAddr = linkLayerAddr;
    }

    public NetworkCard(PcapNetworkInterface pnif) {
        this.name = pnif.getName();
        this.description = pnif.getDescription();

        List<PcapAddress> tmp = pnif.getAddresses();
        Object ipv4Addresses = null;
        for(Object ob : tmp){
            if (ob.getClass().equals(PcapIpV4Address.class)){
                ipv4Addresses = ob;
            }
        }

        if (ipv4Addresses != null){
            PcapIpV4Address addresses = (PcapIpV4Address) ipv4Addresses;
            this.address = String.valueOf(addresses.getAddress());
            this.netmask = String.valueOf(addresses.getNetmask());
            this.broadcastAddr = String.valueOf(addresses.getBroadcastAddress());
            this.linkLayerAddr = String.valueOf(pnif.getLinkLayerAddresses().get(0)).toUpperCase();
        }
    }

    public NetworkCard() {
    }

    public Boolean isValidNetworkCard(){
        return  (this.name != null && this.name.length() != 0) &&
                (this.address != null && this.address.length() != 0) &&
                (this.netmask != null && this.netmask.length() != 0) &&
                this.description.toLowerCase().equals("Microsoft".toLowerCase());
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNetmask() {
        return this.netmask;
    }

    public void setNetmask(String netmask) {
        this.netmask = netmask;
    }

    public String getBroadcastAddr() {
        return this.broadcastAddr;
    }

    public void setBroadcastAddr(String broadcastAddr) {
        this.broadcastAddr = broadcastAddr;
    }

    public String getLinkLayerAddr() {
        return this.linkLayerAddr;
    }

    public void setLinkLayerAddr(String linkLayerAddr) {
        this.linkLayerAddr = linkLayerAddr;
    }
}
