package com.yoa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoa.entity.TMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liulei
 * @since 2021-05-20
 */
@Mapper
@Repository
public interface TMenuMapper extends BaseMapper<TMenu> {

    /**
     * 根据用户ID获取菜单列表
     * */
    List<TMenu> getMenuList(String id);

    /**
     * 范湖所有菜单
     * */
    List<TMenu> getAllMenu();
}
