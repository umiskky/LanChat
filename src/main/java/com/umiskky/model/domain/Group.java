package com.umiskky.model.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.ArrayList;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/14
 */
@Data
public class Group {

    @JSONField(name="name")
    private String name;

    @JSONField(name="icon")
    private String icon;

    @JSONField(name="uuid")
    private String uuid;

    @JSONField(name="key")
    private byte[] key;

    @JSONField(name="groupMembers")
    private ArrayList<User> groupMembers;
}
