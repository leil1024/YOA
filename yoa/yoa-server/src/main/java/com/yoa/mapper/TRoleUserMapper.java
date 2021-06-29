package com.yoa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoa.entity.TRoleUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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
public interface TRoleUserMapper extends BaseMapper<TRoleUser> {

    /**
     * 更新角色用户
     * */
    void updateRole(String userid,String roleid);
}
