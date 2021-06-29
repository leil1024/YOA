package com.yoa.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
public class TRoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;


    private String objId;

    /**
     * 角色ID
     */
    @TableField("roleId")
    private String roleid;

    /**
     * 菜单ID
     */
    @TableField("menuId")
    private String menuid;


}
