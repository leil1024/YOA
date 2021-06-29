package com.yoa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoa.entity.TRoleMenu;
import com.yoa.mapper.TRoleMenuMapper;
import com.yoa.service.ITRoleMenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liulei
 * @since 2021-05-20
 */
@Service
@Slf4j
public class TRoleMenuServiceImpl extends ServiceImpl<TRoleMenuMapper, TRoleMenu> implements ITRoleMenuService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    TRoleMenuMapper tRoleMenuMapper;


    /**
     * huoq对应角色的菜单ID
     * */
    @Override
    public List<String> getMenuID(String roleid) {
        List<String> menuIdList = (List<String>)redisTemplate.opsForValue().get(roleid + "_menu");
        log.info("在redis中获取角色菜单----------");
        if (CollectionUtils.isEmpty(menuIdList)){
            menuIdList = tRoleMenuMapper.getMenuID(roleid);
            redisTemplate.opsForValue().set(roleid + "_menu",menuIdList);
            log.info("redis中无角色菜单数据，在数据库中获取并添加到redis-----------");
        }
        return menuIdList;
    }


    /**
     * 获取用户ID
     * */
    @Override
    public List<String> getUserId(String roleid) {
        List<String> userId = tRoleMenuMapper.getUserId(roleid);
        return userId;
    }


    /**
     * 删除角色对应的菜单
     * */
    @Override
    public void delRoleMent(String roleid, String menuId) {
        tRoleMenuMapper.delRoleMenu(roleid,menuId);
    }


}
