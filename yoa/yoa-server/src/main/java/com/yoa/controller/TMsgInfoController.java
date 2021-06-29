package com.yoa.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.yoa.entity.TMsgInfo;
import com.yoa.entity.TSessionList;
import com.yoa.service.ITMsgInfoService;
import com.yoa.service.ITSessionListService;
import com.yoa.uitl.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 消息表 前端控制器
 * </p>
 *
 * @author liulei
 * @since 2021-06-11
 */
@CrossOrigin
@RestController
@RequestMapping("/msgInfo")
public class TMsgInfoController {


    @Autowired
    private ITSessionListService itSessionListService;

    @Autowired
    private ITMsgInfoService itMsgInfoService;


    //获取消息列表
    //当前用户_to_接收用户的sessionId
    /**
     * 返回与我方有关的所有接受方的消息信息
     * */
    @GetMapping("/msgList")
    public Result msgInfoList(@RequestParam String sessionId){
        System.out.println(sessionId);
        //获取sessionLits
        TSessionList tSessionList = itSessionListService.getOne(new QueryWrapper<TSessionList>().eq("obj_id", sessionId));
        //获取聊天记录
        List<Object> msgList = itMsgInfoService.getMsgList(tSessionList.getUserId(), tSessionList.getToUserId());

        //更新消息已读(当前用户_to_接收用户)
        itMsgInfoService.update(new UpdateWrapper<TMsgInfo>()
                .set("un_read_flag",1)
                .eq("from_user_id",tSessionList.getUserId())
                .eq("to_user_id",tSessionList.getToUserId()));

        return Result.succ(msgList);
    }
}




















