package com.yoa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yoa.entity.TRoleUser;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liulei
 * @since 2021-05-20
 */
public interface ITRoleUserService extends IService<TRoleUser> {

    //更新用户角色
    void updateRole(String userid, String roleid);
}
