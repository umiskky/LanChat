import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.umiskky.model.entity.LocalUser;

import java.nio.charset.StandardCharsets;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/23
 */
public class Test2 {
    @Test
    public void test2(){

        LocalUser localUser = new LocalUser();
        localUser.setUuid("dae21dc726f34992b69ae0fba3ae0081");
        localUser.setNickname("umiskky");
        localUser.setIpAddress("10.10.10.10");
        localUser.setAvatarId(5);
        localUser.setKey("umiskky".getBytes());

        JSONArray payloadJson = new JSONArray();
        JSONObject nickname = new JSONObject();
        nickname.put("nickname", localUser.getNickname()==null ? "" : localUser.getNickname());
        payloadJson.add(nickname);
        String payload = payloadJson.toJSONString();

        System.out.println(payload);

        byte[] rawPayload = payload.getBytes(StandardCharsets.UTF_8);
        System.out.println(rawPayload);

        String res = JSON.parseArray(new String(rawPayload, StandardCharsets.UTF_8)).getJSONObject(0).getString("nickname");
        System.out.println(res);
    }
}
