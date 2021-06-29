package com.yoa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yoa.entity.TRole;
import com.yoa.uitl.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liulei
 * @since 2021-05-20
 */
public interface ITRoleService extends IService<TRole> {

    /**
     * 删除角色
     * */
    Result delRole(String objId);

    /**
     * 更新角色
     * */
    Result updateRole(TRole tRole);


    /**
     * 角色添加
     * */
    Result addRole(String name);

    /**
     * 已启用的角色
     * */
    List<TRole> allRoleList();
}
