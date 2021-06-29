package com.yoa.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yoa.entity.TSessionList;
import com.yoa.entity.TUser;
import com.yoa.service.ITSessionListService;
import com.yoa.service.ITUserService;
import com.yoa.uitl.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * <p>
 * 会话列表 前端控制器
 * </p>
 *
 * @author liulei
 * @since 2021-06-11
 */
@CrossOrigin
@RestController
public class TSessionListController {


    @Autowired
    ITSessionListService itSessionListService;
    @Autowired
    ITUserService itUserServicel;




    /**
     * 建立会话连接，保存消息的发送者，接收者以及消息未读数量
     * 获取前端传入的参数，实例化一个TSessionList实体类
     *      userId：当前登陆用户的ID
     *      toUserId：接收者的ID
     *      listName：接收者的名称
     *
     * 我方与接收方建立连接，接收方与我方建立连接
     *
     * @return id
     * 返回我方与对方会话连接的ID
     * */
    @PostMapping("/createSession")
    public Result createSession(TSessionList tSessionList){

        String id = "";

        //获取当前用户
        TUser User = itUserServicel.getById(tSessionList.getUserId());

        //判断接收方和我方有无连接，没有的话建立连接
        //接受方的ID设置为当前用户(我方)ID
        int userIdNumber = itSessionListService.count(new QueryWrapper<TSessionList>().eq("to_user_id", tSessionList.getUserId()).eq("user_id",tSessionList.getToUserId()));
        if (userIdNumber == 0){
            TSessionList tSessionList_other = new TSessionList();
            String OtherId = UUID.randomUUID().toString().replaceAll("-","");
            tSessionList_other.setObjId(OtherId);
            tSessionList_other.setUserId(tSessionList.getToUserId());
            tSessionList_other.setToUserId(tSessionList.getUserId());
            tSessionList_other.setListName(tSessionList.getListName()+"_to_"+User.getUsername());
            tSessionList_other.setUnReadCount(0);
            itSessionListService.save(tSessionList_other);
        }
        //我方和对方建立连接
        int number = itSessionListService.count(new QueryWrapper<TSessionList>().eq("user_id", tSessionList.getUserId()).eq("to_user_id", tSessionList.getToUserId()));
        if (number == 0){
            //我方与接收方建立连接
            //接收前端当前用户ID，接收消息用户ID，接收消息用户名称
            //后端设置主键，消息未读数量
            id = UUID.randomUUID().toString().replaceAll("-","");
            tSessionList.setObjId(id);
            tSessionList.setUnReadCount(0);
            tSessionList.setListName(User.getUsername()+"_to_"+tSessionList.getListName());
            itSessionListService.save(tSessionList);
        }else {
            id = itSessionListService.getOne(new QueryWrapper<TSessionList>().eq("user_id", tSessionList.getUserId()).eq("to_user_id",tSessionList.getToUserId())).getObjId();
        }

        return Result.succ(null,id);
    }

}

















