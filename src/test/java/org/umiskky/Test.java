package org.umiskky;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.umiskky.service.pcaplib.packet.domain.Uuid;
import org.umiskky.service.task.InitTask;

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

        ArrayList<Uuid> groupMembers = new ArrayList<>();
        groupMembers.add(Uuid.getInstance(IdUtil.simpleUUID()));
        groupMembers.add(Uuid.getInstance(IdUtil.simpleUUID()));
        String payload = JSON.toJSONString(groupMembers);
        System.out.println(payload);

        JSONArray list = JSON.parseArray(payload);
        JSONObject uuid1 = list.getJSONObject(0);
        uuid1.get("uuid");
        ArrayList<Uuid> uuids = new ArrayList<>();
        for(int i=0; i<list.size(); i++){
            uuids.add(Uuid.getInstance(list.getJSONObject(i).get("uuid").toString()));
        }
        System.out.println(uuids);

    }
}
