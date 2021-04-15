package org.umiskky.model.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/14
 */
@Data
public class Message implements Serializable {

    @Serial
    private static final long serialVersionUID = -3367091732578970423L;

    @JSONField(name="toUuid")
    private String toUuid;

    @JSONField(name="fromUuid")
    private String fromUuid;

    @JSONField(name="sendTime")
    private String sendTime;

    @JSONField(name="message")
    private String message;
}
