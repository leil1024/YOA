package com.yoa.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yoa.entity.TUser;
import com.yoa.service.ITUserService;
import com.yoa.uitl.Result;
import org.apache.ibatis.session.ResultContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.geom.RectangularShape;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liulei
 * @since 2021-04-21
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class TUserController{

    @Autowired
    ITUserService itUserService;

    /**
     * 获取所有系统用户信息
     * */
    @GetMapping("/userList")
    public Result getUserList(){
        List<TUser> userList = itUserService.getUserList();
        return Result.succ(null,userList);
    }

    @GetMapping("/elseUser")
    public Result getElseUser(@RequestParam String userId){
//        查出除当前用户外的所有用户以及消息未读数量
        List<TUser> userList = itUserService.chatUserList(userId);
        return Result.succ(null,userList);
    }

}
