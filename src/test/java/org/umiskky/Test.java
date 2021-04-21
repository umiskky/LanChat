package org.umiskky;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.pcap4j.packet.namednumber.TcpPort;
import org.slf4j.Logger;
import org.umiskky.service.pcaplib.packet.domain.Uuid;
import org.umiskky.service.pcaplib.utils.IpAddressTools;
import org.umiskky.service.task.InitTask;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/14
 */
public class Test {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Test.class);

    @org.junit.Test
    public void test(){
        System.out.println(InitTask.class.getResource("/").getPath());

        JSONArray jsonObject = JSON.parseArray("[{'nickname':'umiskky'}]");
        System.out.println(jsonObject.getJSONObject(0).getString("nickname"));

        System.out.println(Arrays.toString(Uuid.invalidUuid.toByteArray()));
        System.out.println(Uuid.invalidUuid.toByteArray().length);
        System.out.println(Arrays.toString(Uuid.getInstance(IdUtil.simpleUUID()).toByteArray()));
        System.out.println(Uuid.getInstance(IdUtil.simpleUUID()).toByteArray().length);

        ArrayList<String> groupMembers = new ArrayList<>();
        groupMembers.add(Uuid.getInstance(IdUtil.simpleUUID()).getUuid());
        groupMembers.add(Uuid.getInstance(IdUtil.simpleUUID()).getUuid());
        String payload = JSON.toJSONString(groupMembers);
        System.out.println(payload.getBytes(StandardCharsets.UTF_8));
        System.out.println(new String(payload.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));

        JSONArray list = JSON.parseArray(new String(payload.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
        ArrayList<String> uuids = new ArrayList<>();
        for(int i=0; i<list.size(); i++){
            uuids.add((String) list.get(i));
        }
        System.out.println(uuids);

        try {
            System.out.println(Arrays.toString(((Inet4Address) InetAddress.getByName("192.168.0.2".replace("/", ""))).getAddress()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        byte[] test = new byte[4];
        try {
            test = ((Inet4Address) InetAddress.getByName("10.1.0.2".replace("/", ""))).getAddress();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println((int) test[0]);

        try {
            System.out.println(IpAddressTools.ipAddressToString((Inet4Address) InetAddress.getByName("10.168.0.2".replace("/", ""))));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        };

        byte byte1 = -64;
        System.out.println(byte1);
        System.out.println(0xFFFF & byte1);

        System.out.println(TcpPort.getInstance((short) 8888).valueAsInt());
    }
}
