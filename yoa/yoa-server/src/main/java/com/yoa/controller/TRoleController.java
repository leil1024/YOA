
package com.yoa.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yoa.entity.TRole;
import com.yoa.service.ITRoleService;
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
 * @since 2021-05-20
 */
@CrossOrigin
@RestController
@RequestMapping("/role")
public class TRoleController {

    @Autowired
    ITRoleService itRoleService;


    /**
     * 分页角色列表
     * */
    @RequestMapping("/rolelist")
    public Result roleList(@RequestParam(defaultValue = "1") Integer PageNumber){
        Page page = new Page(PageNumber, 5);
        Page role = itRoleService.page(page, new QueryWrapper<TRole>().orderByDesc("create_time"));
        return Result.succ(null,role);
    }

    /**
     * 已启用的有角色
     * */
    @GetMapping("/enableRole")
    public Result allRoleList(){
        List<TRole> roleList = itRoleService.allRoleList();
        return Result.succ(null,roleList);
    }

    @GetMapping("/allrole")
    public Result roleList(){
        List<TRole> roleList = itRoleService.list();
        return Result.succ(roleList);
    }

    //角色删除
    @PostMapping("/delRole")
    public Result delRole(String objId){
        System.out.println(objId);
        return itRoleService.delRole(objId);
    }

    //角色更新
    @PostMapping("/upRole")
    public Result updateRole(TRole tRole){
        return itRoleService.updateRole(tRole);
    }

    //角色添加
    @PostMapping("/addRole")
    public Result addRole(String name){
        return itRoleService.addRole(name);
    }
}
