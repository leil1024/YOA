package com.yoa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoa.entity.TRoleMenu;
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
public interface TRoleMenuMapper extends BaseMapper<TRoleMenu> {

    List<String> getMenuID(String roleid);

    List<String> getUserId(String roleid);

    /**
     * 删除角色对应的菜单
     * */
    void delRoleMenu(String roleid, String menuid);
}
