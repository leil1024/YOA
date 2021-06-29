package com.yoa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoa.entity.TRole;
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
public interface TRoleMapper extends BaseMapper<TRole> {

    /**
     * 已启用的所有角色
     * */
    List<TRole> allRoleList();
}
