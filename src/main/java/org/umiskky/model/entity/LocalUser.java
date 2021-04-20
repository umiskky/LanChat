package org.umiskky.model.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/18
 */
@Entity
public class LocalUser{
    @Id
    private long id;
    @Index
    private String uuid;
    private String nickname;
    private String ipAddress;
    private int serverPort;
    private int avatarId;
    private byte[] key;

    public LocalUser() {
    }

    public LocalUser(long id, String uuid, String nickname, String ipAddress, int serverPort, int avatarId, byte[] key) {
        this.id = id;
        this.uuid = uuid;
        this.nickname = nickname;
        this.ipAddress = ipAddress;
        this.serverPort = serverPort;
        this.avatarId = avatarId;
        this.key = key;
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

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }
}
