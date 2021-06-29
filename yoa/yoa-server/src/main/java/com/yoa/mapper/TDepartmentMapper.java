package com.yoa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoa.entity.TDepartment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liulei
 * @since 2021-05-16
 */
@Mapper
@Repository
public interface TDepartmentMapper extends BaseMapper<TDepartment> {


    /**
     * 获取所有部门
     * */
    List<Deprecated> getAllDepartments(Integer parentId);

    /**
     * 添加部门
     * */
    void appDep(TDepartment tDepartment);

    /**
     * 删除部门
     * */
    void delDep(TDepartment tDepartment);
}
