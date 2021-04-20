package org.umiskky.model.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/14
 */
@Entity
public class Friend {
    @Id
    private long id;
    @Index
    private String uuid;

    private String nickname;

    private String icon;

    private int avatarId;

    private String ipAddress;

    private int serverPort;

    private byte[] key;

    private Boolean status;

    private long lastUpdated;

    public Friend() {
    }

    public Friend(long id, String uuid, String nickname, String icon, int avatarId, String ipAddress, int serverPort, byte[] key, Boolean status, long lastUpdated) {
        this.id = id;
        this.uuid = uuid;
        this.nickname = nickname;
        this.icon = icon;
        this.avatarId = avatarId;
        this.ipAddress = ipAddress;
        this.serverPort = serverPort;
        this.key = key;
        this.status = status;
        this.lastUpdated = lastUpdated;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
