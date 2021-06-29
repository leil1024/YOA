package com.yoa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoa.entity.TRole;
import com.yoa.mapper.TRoleMapper;
import com.yoa.service.ITRoleService;
import com.yoa.uitl.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liulei
 * @since 2021-05-20
 */
@Service
public class TRoleServiceImpl extends ServiceImpl<TRoleMapper, TRole> implements ITRoleService {

    @Autowired
    TRoleMapper tRoleMapper;

    @Override
    public Result delRole(String objId) {
        System.out.println(objId);
        boolean result = remove(new QueryWrapper<TRole>().eq("obj_id", objId));
        if (!result){
            Result.succ("删除失败");
        }
        return Result.succ("删除成功");
    }

    /**
     * 更新职位
     * */
    @Override
    public Result updateRole(TRole tRole) {
        boolean result = update(tRole,new QueryWrapper<TRole>().eq("obj_id",tRole.getObjId()));
        String message = "更新成功";
        if (!result){
            message = "更新失败";
        }
        return Result.succ(message);
    }

    @Override
    public Result addRole(String name) {
        String id = UUID.randomUUID().toString().replaceAll("-","");
        boolean result = save(new TRole(id, name, null, 1));
        String message = "添加成功";
        if (!result){
            message = "添加失败";
        }
        return Result.succ(message);
    }

    /**
     * 已启用的所有角色
     * */
    @Override
    public List<TRole> allRoleList() {
        List<TRole> roleList = tRoleMapper.allRoleList();
        return roleList;
    }
}
