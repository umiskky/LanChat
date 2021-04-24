package org.umiskky.model.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/21
 */
@Entity
public class Apply {

    @Id
    private long id;
    private String uuid;
    private String groupUuid;
    private Boolean isGroup;
    private byte[] key;

    public Apply() {
    }

    public Apply(long id, String uuid, String groupUuid, Boolean isGroup) {
        this.id = id;
        this.uuid = uuid;
        this.groupUuid = groupUuid;
        this.isGroup = isGroup;
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

    public String getGroupUuid() {
        return groupUuid;
    }

    public void setGroupUuid(String groupUuid) {
        this.groupUuid = groupUuid;
    }

    public Boolean getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(Boolean group) {
        isGroup = group;
    }

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String ls = System.getProperty("line.separator");

        sb.append("[Apply]").append(ls);
        sb.append("  Uuid: ").append(this.uuid).append(ls);
        sb.append("  Group Uuid: ").append(this.groupUuid).append(ls);
        sb.append("  Is Group: ").append(this.isGroup).append(ls);

        return sb.toString();
    }
}
