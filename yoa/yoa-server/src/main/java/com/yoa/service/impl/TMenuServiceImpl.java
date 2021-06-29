package com.yoa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoa.entity.TMenu;
import com.yoa.mapper.TMenuMapper;
import com.yoa.service.ITMenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.apache.shiro.SecurityUtils.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liulei
 * @since 2021-05-20
 */
@Slf4j
@Service
public class TMenuServiceImpl extends ServiceImpl<TMenuMapper, TMenu> implements ITMenuService {

    @Autowired
    TMenuMapper tMenuMapper;
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 根据用户ID返回菜单
     * */
    @Override
    //前端Login.vue登录之后刷新页面，导致方法执行两遍
    public List<TMenu> getMenuList(String id) {
        log.info("----------获取登陆用户id：" + id + ",开始获取用户菜单");
        List<TMenu> tMenuList = (List<TMenu>) redisTemplate.opsForValue().get("menu_" + id);
        log.info("----------在redis中获取用户菜单信息----"+id+"------");
        if (CollectionUtils.isEmpty(tMenuList)){
            tMenuList = tMenuMapper.getMenuList(id);
            redisTemplate.opsForValue().set("menu_"+id,tMenuList);
            log.info("-----------在mysql中获取用户信息并设置redis缓存---"+id+"------");
        }
        return tMenuList;
    }


    /**
     * 返回所有菜单
     * */
    @Override
    public List<TMenu> getAllMenu() {
        List<TMenu> allMenuList = (List<TMenu>) redisTemplate.opsForValue().get("allMenu");
        if (CollectionUtils.isEmpty(allMenuList)){
            allMenuList = tMenuMapper.getAllMenu();
            redisTemplate.opsForValue().set("allMenu",allMenuList);
            log.info("------------在redis中缓存所有菜单列表");
        }
        return allMenuList;
    }
}
