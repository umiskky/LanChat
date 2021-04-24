package org.umiskky.model.verification;

import org.umiskky.model.entity.User;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/24
 */
public class UserVerification {

    /**
     * @description The method isValidUser is used to verify a user is valid or not.
     * @param
     * @return java.lang.Boolean
     * @author umiskky
     * @date 2021/4/24-8:39
     */
    public static Boolean isValidUser(User user){
        final int UUID_LENGTH = 32;
        if(user == null){
            return false;
        }
        System.out.println(user.getUuid());
        return user.getUuid().length()==UUID_LENGTH
                && NetworkInterfaceVerification.isValidIpAddress(user.getIpAddress())
                && NetworkInterfaceVerification.isValidIpPort(user.getServerPort())
                && NetworkInterfaceVerification.isValidLinkLayerAddress(user.getLinkLayerAddress());
    }
}
