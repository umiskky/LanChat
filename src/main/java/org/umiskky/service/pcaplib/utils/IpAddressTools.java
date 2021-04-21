package org.umiskky.service.pcaplib.utils;

import java.net.Inet4Address;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/21
 */
public class IpAddressTools {

    /**
     * @description The method ipAddressToString is used to convert Inet4Address to address string without hostname.
     * @param inet4Address
     * @return java.lang.String
     * @author umiskky
     * @date 2021/4/21-14:08
     */
    public static String ipAddressToString(Inet4Address inet4Address){
        StringBuilder stringBuilder = new StringBuilder();
        byte[] address = inet4Address.getAddress();
        for(int idx=0; idx<address.length; idx++){
            stringBuilder.append(Integer.toString((int) address[idx] < 0 ? (int) address[idx] + 256 : (int) address[idx]));
            if(idx < address.length-1){
                stringBuilder.append(".");
            }
        }
        return stringBuilder.toString();
    }
}
