package com.yoa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author liulei
 * @since 2021-05-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private String objId;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 创建时间
     * */
    private LocalDateTime createTime;

    /**
     * 是否启用
     * */
    private Integer enable;

}
