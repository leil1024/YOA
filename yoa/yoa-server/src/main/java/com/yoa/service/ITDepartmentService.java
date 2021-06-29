package com.yoa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yoa.entity.TDepartment;
import com.yoa.uitl.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liulei
 * @since 2021-05-16
 */
public interface ITDepartmentService extends IService<TDepartment> {

    /**
     * 获取所有部门
     * @return
     * */
    List<Deprecated> getAllDepartments();

    /**
     * 添加部门
     * */
    Result appDep(TDepartment tDepartment);

    /**
     * 删除部门
     * */
    Result delDep(Integer id);
}
