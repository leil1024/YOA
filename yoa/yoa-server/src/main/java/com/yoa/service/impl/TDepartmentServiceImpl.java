package com.yoa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoa.entity.TDepartment;
import com.yoa.mapper.TDepartmentMapper;
import com.yoa.service.ITDepartmentService;
import com.yoa.uitl.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liulei
 * @since 2021-05-16
 */
@Service
public class TDepartmentServiceImpl extends ServiceImpl<TDepartmentMapper, TDepartment> implements ITDepartmentService {


    @Autowired
    private TDepartmentMapper tDepartmentMapper;

    /***
     *获取所有部门，树形结构
     */
    @Override
    public List<Deprecated> getAllDepartments() {
        return tDepartmentMapper.getAllDepartments(-1);
    }

    /**
     * 添加部门
     * */
    @Override
    public Result appDep(TDepartment tDepartment) {
        tDepartment.setEnable(true);
        tDepartmentMapper.appDep(tDepartment);
        /**
         * 在创建的mysql存储过程中，result等于插入之后受影响的行数
         * select row_count() into result;
         * */
        if(1 == tDepartment.getResult()){
            return Result.succ("添加成功",tDepartment);
        }
        return Result.fail(400,"添加失败");
    }

    /**
     * 删除部门
     * */
    @Override
    public Result delDep(Integer id) {
        TDepartment tDepartment = new TDepartment();
        tDepartment.setId(id);
        tDepartmentMapper.delDep(tDepartment);
        String message = "";
        switch (tDepartment.getResult()){
            case -2:
                return Result.fail("此部门下存在子部门，不允许删除");
            case -1:
                return Result.fail("此部门下存在员工，不允许删除");
            case 1:
                return Result.succ("删除成功");
            default:
                return Result.fail("删除失败");
        }
    }

}
