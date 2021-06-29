package com.yoa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yoa.entity.TUser;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liulei
 * @since 2021-04-21
 */
public interface ITUserService extends IService<TUser> {

    /**
     * 根据用户名获取用户信息
     * @param username
     * */
    TUser getUser(String username);

    List<TUser> getUserList();

    List<TUser> chatUserList(String activeUserId);

}
