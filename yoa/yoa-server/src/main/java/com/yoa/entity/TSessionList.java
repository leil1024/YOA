package com.yoa.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 会话列表
 * </p>
 *
 * @author liulei
 * @since 2021-06-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TSessionList implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String objId;

    /**
     * 所属用户
     */
    private String userId;

    /**
     * 到用户
     */
    private String toUserId;

    /**
     * 会话名称
     */
    private String listName;

    /**
     * 未读消息数
     */
    private Integer unReadCount;


}
