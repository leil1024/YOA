package com.yoa.controller;


import com.yoa.entity.TRoleMenu;
import com.yoa.service.ITRoleMenuService;
import com.yoa.uitl.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
@RequestMapping("/roleMenu")
public class TRoleMenuController {

    @Autowired
    ITRoleMenuService itRoleMenuService;

    @Autowired
    RedisTemplate redisTemplate;
    /**
     * 根据角色ID获取菜单ID
     * @param roleid
     * @return
     */
    @PostMapping("/menuId")
    public Result getMenuID(String roleid){
        return Result.succ(null,itRoleMenuService.getMenuID(roleid));
    }


    /**
     * 插入角色ID对应的菜单ID
     * @param roleid
     * @param selectMenuIdList
     */
    @PostMapping("/setRoleMenu")
    public Result setRoleMent(@RequestParam(value = "roleid") String roleid, @RequestParam(value = "selectMenuIdList") List<String> selectMenuIdList){

        List<String> menuIDList = itRoleMenuService.getMenuID(roleid);
        for (String menuId : selectMenuIdList) {
            if (!menuIDList.contains(menuId)){
                System.out.println(menuId);
                String objId = UUID.randomUUID().toString().replaceAll("-","");
                itRoleMenuService.save(new TRoleMenu(objId,roleid,menuId));
            }
        }
        //删除角色对应的菜单缓存
        redisTemplate.delete(roleid+"_menu");
        //删除角色对应的所有用户菜单的缓存
        for (String userId : itRoleMenuService.getUserId(roleid)) {
            redisTemplate.delete("menu_" + userId);
        }
        return Result.succ("更新成功");
    }


    @PostMapping("/delRoleMenu")
    public Result delRoleMent(@RequestParam(value = "roleid") String roleid,@RequestParam(value = "selectMenuIdList") List<String> selectMenuList){
        //该角色对应的菜单
        List<String> menuIDList = itRoleMenuService.getMenuID(roleid);
        //遍历上传的菜单列表,
        for (String menuId : menuIDList) {
            if (!selectMenuList.contains(menuId)){
                itRoleMenuService.delRoleMent(roleid,menuId);
            }
        }
        //删除角色对应的菜单缓存
        redisTemplate.delete(roleid+"_menu");
        //删除角色对应的所有用户菜单的缓存
        for (String userId : itRoleMenuService.getUserId(roleid)) {
            redisTemplate.delete("menu_" + userId);
        }
        return Result.succ("更新成功");
    }
}
