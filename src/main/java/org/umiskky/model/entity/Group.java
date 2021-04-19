package org.umiskky.model.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.relation.ToMany;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/14
 */
@Entity
public class Group {
    public ToMany<GroupMember> groupMembers;
    @Id
    private long id;
    private String name;
    private String icon;
    private byte[] key;
    @Index
    private String uuid;

    public Group() {
    }

    public Group(long id, String uuid, String name, String icon, byte[] key) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.icon = icon;
        this.key = key;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

//    public ToMany<GroupMember> getGroupMembers() {
//        return groupMembers;
//    }
//
//    public void setGroupMembers(ToMany<GroupMember> groupMembers) {
//        this.groupMembers = groupMembers;
//    }
}
