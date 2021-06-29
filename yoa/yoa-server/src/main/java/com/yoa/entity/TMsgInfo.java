package com.yoa.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 消息表
 * </p>
 *
 * @author liulei
 * @since 2021-06-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TMsgInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息id
     */
    private String objId;

    /**
     * 消息发送者id
     */
    private String fromUserId;

    /**
     * 消息发送者名称
     */
    private String fromUserName;

    /**
     * 消息接收者id
     */
    private String toUserId;

    /**
     * 消息接收者名称
     */
    private String toUserName;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息发送时间
     */
    private LocalDateTime createTime;

    /**
     * 是否已读（1 已读）
     */
    private Integer unReadFlag;


    /**
     * 是否己方发送
     * */
    private String self;


    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getCreateTime() {
        return createTime;
    }
}
