package org.umiskky.model.verification;

import org.umiskky.model.entity.Friend;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/24
 */
public class FriendVerification {
    /**
     * @description The method isValidFriend is used to verify a friend is valid or not.
     * @param friend
     * @return java.lang.Boolean
     * @author umiskky
     * @date 2021/4/24-10:58
     */
    public static Boolean isValidFriend(Friend friend){
        final int UUID_LENGTH = 32;
        if(friend == null){
            return false;
        }
        return friend.getUuid().length()==UUID_LENGTH
                && NetworkInterfaceVerification.isValidIpAddress(friend.getIpAddress())
                && NetworkInterfaceVerification.isValidIpPort(friend.getServerPort())
                && NetworkInterfaceVerification.isValidAesKey(friend.getKey());
    }
}
