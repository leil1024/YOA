package com.yoa.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yoa.entity.TDepartment;
import com.yoa.service.ITDepartmentService;
import com.yoa.uitl.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liulei
 * @since 2021-05-16
 */
@CrossOrigin
@RestController
@RequestMapping("/system/department")
public class TDepartmentController {

    @Autowired
    private ITDepartmentService itDepartmentService;


    /**
     * 部门列表，树形结构
     * */
    @GetMapping("/")
    public Result getAllDepartments(){
        List<Deprecated> allDepartments = itDepartmentService.getAllDepartments();
        return Result.succ(null,allDepartments);
    }

    /**
     * 部门列表
     * */
    @GetMapping("/enableDep")
    public Result getDepartment(){
        List<TDepartment> tDepartments = itDepartmentService.list(new QueryWrapper<TDepartment>().eq("enable", 1));
        return Result.succ(null,tDepartments);
    }


    /**
     * 添加部门
     * */
    @PostMapping("/appDep")
    public Result appDep(TDepartment tDepartment){
        return itDepartmentService.appDep(tDepartment);
    }


    /**
     * 删除部门
     * */
    @PostMapping("/delDep/{id}")
    public Result delDep(@PathVariable Integer id){
        System.out.println(id);
        return itDepartmentService.delDep(id);
    }
}
