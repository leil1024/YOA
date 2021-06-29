package com.yoa.controller;


import com.yoa.entity.TEmployee;
import com.yoa.service.ITEmployeeService;
import com.yoa.uitl.ExcelUtil;
import com.yoa.uitl.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liulei
 * @since 2021-05-19
 */
@CrossOrigin
@RestController
@RequestMapping("/employee")
public class TEmployeeController {

    @Autowired
    ITEmployeeService itEmployeeService;


    /**
     * 返回所有员工信息列表
     * */
    @GetMapping("/")
    public Result getEmployees(){
        List<TEmployee> employees = itEmployeeService.getEmployees();
        return Result.succ(null,employees);
    }

    /**
     * 添加员工
     * */
    @PostMapping("/addEmp")
    public Result addEmp(TEmployee tEmployee){
        itEmployeeService.addEmp(tEmployee);
        return Result.succ("添加成功");
    }


    /**
     * 获取个人信息
     * */
    @PostMapping("/getMyData")
    public Result getMyData(String userid){
        TEmployee tEmployee = itEmployeeService.getMyData(userid);
        return Result.succ(null,tEmployee);
    }

    /**
     * 员工信息导出
     * */
    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response){
        List<TEmployee> employees = itEmployeeService.getEmployees();
        ExcelUtil.exportExcel(employees,"员工信息","sheet1",TEmployee.class,"员工信息.xls",response);
    }
}
