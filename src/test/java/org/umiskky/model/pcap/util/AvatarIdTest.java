package org.umiskky.model.pcap.util;

import org.junit.Test;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/15
 */
public class AvatarIdTest {
    @Test
    public void testAvatarId(){
        AvatarId test = new AvatarId((byte)1);
        System.out.println(test.getAvatarId());
    }
}
