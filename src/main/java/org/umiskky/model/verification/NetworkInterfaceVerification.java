package org.umiskky.model.verification;

import org.pcap4j.util.MacAddress;
import org.slf4j.Logger;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/24
 */
public class NetworkInterfaceVerification {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(NetworkInterfaceVerification.class);

    /**
     * @description The method isValidIpAddress is used to verify an IP address is valid or not.
     * @param ipAddress
     * @return java.lang.Boolean
     * @author umiskky
     * @date 2021/4/24-8:58
     */
    public static Boolean isValidIpAddress(String ipAddress){
        if(ipAddress == null || ipAddress.isEmpty()){
            return false;
        }
        try {
            Inet4Address.getByName(ipAddress);
        } catch (UnknownHostException e) {
            log.debug(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * @description The method isValidIpPort is used to verify a socket port is valid or not.
     * @param port
     * @return java.lang.Boolean
     * @author umiskky
     * @date 2021/4/24-8:59
     */
    public static Boolean isValidIpPort(int port){
        return port > 0 && port < 65535;
    }

    /**
     * @description The method isValidLinkLayerAddress is used to verify a link layer address is valid or not.
     * @param linkLayerAddress
     * @return java.lang.Boolean
     * @author umiskky
     * @date 2021/4/24-9:00
     */
    public static Boolean isValidLinkLayerAddress(String linkLayerAddress){
        try {
            MacAddress.getByName(linkLayerAddress);
        } catch (IllegalArgumentException e){
            log.debug(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * @description The method isValidAesKey is used to verify a key is valid or not.
     * @param key
     * @return java.lang.Boolean
     * @author umiskky
     * @date 2021/4/24-10:57
     */
    public static Boolean isValidAesKey(byte[] key){
        final int KEY_LENGTH = 16;
        return key != null && key.length == KEY_LENGTH;
    }
}
