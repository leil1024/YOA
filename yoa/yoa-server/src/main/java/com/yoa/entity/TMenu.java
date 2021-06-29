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
 * @since 2021-05-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    private String objId;

    /**
     * 菜单路径
     */
    private String path;

    /**
     * 组件名称
     */
    private String component;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 图标
     */
    private String icon;

    /**
     * 父菜单ID
     */
    @TableField("parentId")
    private String parentId;

    /**
     * 是否启用
     */
    private Boolean enabled;


    /**
     * 用来保存子菜单
     * */
    @TableField(exist = false)
    private List<TMenu> children;

}
