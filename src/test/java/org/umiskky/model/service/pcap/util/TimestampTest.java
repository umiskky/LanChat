package org.umiskky.model.service.pcap.util;

import org.junit.Test;

import java.time.Instant;
import java.time.ZoneId;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/15
 */
public class TimestampTest {
    @Test
    public void testTimestamp(){
        Instant now = Instant.now();
        System.out.println(now);
        System.out.println(now);
        System.out.println(now.getEpochSecond()); // 秒
        System.out.println(now.toEpochMilli()); // 毫秒
        System.out.println(now.atZone(ZoneId.systemDefault()));
    }
}
