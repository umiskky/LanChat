package org.umiskky.model.entity;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import org.slf4j.Logger;

import java.time.Instant;
import java.time.ZoneId;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/14
 */
@Entity
public class Message implements Cloneable{
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Message.class);
    @Id
    private long id;
    @Index
    private String sessionId;

    private String fromUuid;

    private String toUuid;

    private long sendTime;

    private String message;

    public Message() {
    }

    public Message(long id, String sessionId,String fromUuid, String toUuid, long sendTime, String message) {
        this.id = id;
        this.sessionId = sessionId;
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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * @description The method encryptMessage is used to encrypt the message.
     * @param message
     * @param key
     * @return org.umiskky.model.entity.Message
     * @author umiskky
     * @date 2021/4/23-15:44
     */
    public static Message encryptMessage(Message message, byte[] key){
        final int KEY_LENGTH = 16;
        if(key.length == KEY_LENGTH){
            SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
            String encryptHex = aes.encryptHex(message.getMessage());
            message.setMessage(encryptHex);
            log.info("Encrypt the message successfully.");
        }else{
            log.error("Encrypt the message failed because the invalid key length!");
        }
        return message;
    }

    /**
     * @description The method decryptMessage is used to decrypt the message.
     * @param message
     * @param key
     * @return org.umiskky.model.entity.Message
     * @author umiskky
     * @date 2021/4/23-15:45
     */
    public static Message decryptMessage(Message message, byte[] key){
        final int KEY_LENGTH = 16;
        if(key.length == KEY_LENGTH){
            SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
            String decryptStr = aes.decryptStr(message.getMessage(), CharsetUtil.CHARSET_UTF_8);
            message.setMessage(decryptStr);
            log.info("Decrypt the message successfully.");
        }else{
            log.error("Decrypt the message failed because the invalid key length!");
        }
        return message;
    }

    @Override
    protected Object clone(){
        Message message = null;
        try {
            message = (Message) super.clone();
        } catch (CloneNotSupportedException e) {
            log.error(e.getMessage());
        }
        if(message != null){
            message.setId(0);
        }
        return message;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String ls = System.getProperty("line.separator");

        sb.append("[Message]").append(ls);
        sb.append("  From: ").append(this.fromUuid).append(ls);
        sb.append("  To: ").append(this.toUuid).append(ls);
        sb.append("  Send Time: ").append(Instant.ofEpochMilli(this.sendTime).atZone(ZoneId.systemDefault())).append(ls);
        sb.append("  Receive Time: ").append(Instant.now().atZone(ZoneId.systemDefault())).append(ls);
        sb.append("  Message: ").append(this.message).append(ls);

        return sb.toString();
    }
}
