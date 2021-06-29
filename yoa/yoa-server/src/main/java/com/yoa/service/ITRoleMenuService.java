package com.yoa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yoa.entity.TRoleMenu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liulei
 * @since 2021-05-20
 */
public interface ITRoleMenuService extends IService<TRoleMenu> {

    /**
     * 根据角色ID获取菜单ID
     * */
    List<String> getMenuID(String objId);


    /**
     * 根据角色ID获取用户ID
     * */
    List<String> getUserId(String roleid);

    /**
     * 删除角色对应的菜单
     * */
    void delRoleMent(String roleid, String menuId);
}
