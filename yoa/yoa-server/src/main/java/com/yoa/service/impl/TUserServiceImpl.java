package com.yoa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoa.entity.TUser;
import com.yoa.mapper.TUserMapper;
import com.yoa.service.ITUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liulei
 * @since 2021-04-21
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements ITUserService {

    @Autowired
    TUserMapper tUserMapper;

    /**
     * 根据用户名获取用户
     * @param username
     * */
    @Override
    public TUser getUser(String username) {
        QueryWrapper<TUser> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("username",username);
        return getOne(objectQueryWrapper);
    }

    /**
     *获取所有用户信息
     * */
    @Override
    public List<TUser> getUserList() {
        List<TUser> userList = tUserMapper.getUserList();
        return userList;
    }


    /**
     * 获取有关于消息记录的用户信息
     * */
    @Override
    public List<TUser> chatUserList(String activeUserId) {
        //有未读消息记录的用户
        List<TUser> unReadList = tUserMapper.unread(activeUserId);

        //无未读消息记录的用户
        List<String> unReadUserIdList = new ArrayList<String>();
        unReadList.forEach(unReadUser -> {
            unReadUserIdList.add(unReadUser.getId());
        });
        unReadUserIdList.add(activeUserId);
        List<TUser> readList = tUserMapper.read(unReadUserIdList);

        //合并为所有用户
        readList.addAll(unReadList);

        return readList;
    }
}
