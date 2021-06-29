package com.yoa.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author liulei
 * @since 2021-05-07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_position")
public class TPosition{

    private static final long serialVersionUID = 1L;

    /**
     * 职位id
     */
    public String id;

    /**
     * 职位名称
     */
    private String posName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 是否启用
     */
    private Integer enable;


}
