package com.yoa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoa.entity.TEmployee;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liulei
 * @since 2021-05-19
 */
@Mapper
@Repository
public interface TEmployeeMapper extends BaseMapper<TEmployee> {

    /**
     * 获取用户信息列表
     * */
    List<TEmployee> getEmployees();

    /**
     * 获取单个用户信息
     * */
    TEmployee getEmployee(String userid);
}
