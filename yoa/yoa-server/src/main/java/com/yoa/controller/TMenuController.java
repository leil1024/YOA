package com.yoa.controller;


import com.yoa.entity.TMenu;
import com.yoa.service.ITMenuService;
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
@RestController
public class TMenuController {

    @Autowired
    ITMenuService itMenuService;

    /**
     * 根据角色ID返回菜单
     * */
    @CrossOrigin
    @PostMapping("/MenuList")
    public Result menuList(String id){
        List<TMenu> menus = itMenuService.getMenuList(id);
        return Result.succ(null,menus);
    }

    /**
     * 返回所有菜单
     * */
    @CrossOrigin
    @GetMapping("/AllMenu")
    public Result allMenu(){
        List<TMenu> menus = itMenuService.getAllMenu();
        return Result.succ(null,menus);
    }
}
