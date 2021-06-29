package com.yoa.controller;


import com.yoa.service.ITRoleUserService;
import com.yoa.uitl.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/roleuser")
public class TRoleUserController {

    @Autowired
    ITRoleUserService itRoleUserService;

    @Autowired
    RedisTemplate redisTemplate;

    @PostMapping("/updateRole")
    public Result updateRole(@RequestParam(value = "userid") String userid, @RequestParam(value = "roleid") String roleid){
        itRoleUserService.updateRole(userid,roleid);
        //删除redis缓存
        redisTemplate.delete("menu_" + userid);
        return Result.succ("操作成功");
    }

}
