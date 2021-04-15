package org.umiskky.model.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/14
 */
@Data
public class User {

    @JSONField(name="nickname")
    private String nickname;

    @JSONField(name="icon")
    private String icon;

    @JSONField(name="uuid")
    private String uuid;

    @JSONField(name="ipAddress")
    private String ipAddress;

    @JSONField(name="serverPort")
    private int serverPort;

    @JSONField(name="key")
    private byte[] key;

    @JSONField(name="status", serialize=false)
    private Boolean status;

    @JSONField(name="lastUpdated", serialize=false)
    private String lastUpdated;
}
