package com.yoa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoa.entity.TRoleUser;
import com.yoa.mapper.TRoleUserMapper;
import com.yoa.service.ITRoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liulei
 * @since 2021-05-20
 */
@Service
public class TRoleUserServiceImpl extends ServiceImpl<TRoleUserMapper, TRoleUser> implements ITRoleUserService {

    @Autowired
    TRoleUserMapper tRoleUserMapper;

    /**
     * 更新用户角色
     * */
    @Override
    public void updateRole(String userid, String roleid) {
        tRoleUserMapper.updateRole(userid,roleid);
    }
}
