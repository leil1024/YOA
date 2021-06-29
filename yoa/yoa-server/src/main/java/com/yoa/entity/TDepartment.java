package com.yoa.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author liulei
 * @since 2021-05-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TDepartment implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 父部门id
     */
    @TableField("parentId")
    private Integer parentId;

    /**
     * 部门路径
     */
    @TableField("depPath")
    private String depPath;

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 是否上级
     */
    @TableField("isParent")
    private Boolean isParent;

    /**
     * 子部门列表
     * */
    //表示该字段在表中不存在
    @TableField(exist = false)
    private List<Deprecated> children;

    /**
     * 用来保存mysql存储过程中返回的值
     * */
    @TableField(exist = false)
    private Integer result;
}
