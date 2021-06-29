package com.yoa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yoa.entity.TEmployee;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liulei
 * @since 2021-05-19
 */
public interface ITEmployeeService extends IService<TEmployee> {

    /**
     * 获取人员信息列表
     * */
    List<TEmployee> getEmployees();


    /**
     * 添加员工
     * */
    void addEmp(TEmployee tEmployee);

    /**
     * 获取个人信息
     * */
    TEmployee getMyData(String userid);
}
