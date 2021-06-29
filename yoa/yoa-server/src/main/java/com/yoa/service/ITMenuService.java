package com.yoa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yoa.entity.TMenu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liulei
 * @since 2021-05-20
 */
public interface ITMenuService extends IService<TMenu> {

    /**
     * 根据ID返回菜单列表
     * */
    List<TMenu> getMenuList(String id);

    /**
     * 角色菜单视图返回所有菜单
     * */
    List<TMenu> getAllMenu();
}
