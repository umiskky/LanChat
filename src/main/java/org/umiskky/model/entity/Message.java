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
public class Message {
    @Id
    private long id;
    @Index
    private String fromUuid;
    @Index
    private String toUuid;

    private long sendTime;

    private String message;

    public Message() {
    }

    public Message(long id, String fromUuid, String toUuid, long sendTime, String message) {
        this.id = id;
        this.fromUuid = fromUuid;
        this.toUuid = toUuid;
        this.sendTime = sendTime;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFromUuid() {
        return fromUuid;
    }

    public void setFromUuid(String fromUuid) {
        this.fromUuid = fromUuid;
    }

    public String getToUuid() {
        return toUuid;
    }

    public void setToUuid(String toUuid) {
        this.toUuid = toUuid;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
