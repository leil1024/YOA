package com.yoa.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoa.entity.TMsgInfo;
import com.yoa.entity.TUser;
import com.yoa.mapper.TMsgInfoMapper;
import com.yoa.service.ITMsgInfoService;
import com.yoa.service.ITUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author liulei
 * @since 2021-06-11
 */
@Service
public class TMsgInfoServiceImpl extends ServiceImpl<TMsgInfoMapper, TMsgInfo> implements ITMsgInfoService {

    @Autowired
    private ITUserService itUserService;

    @Autowired
    private TMsgInfoMapper tMsgInfoMapper;


    /**
     * 获取与我方有关的所有接受方的信息
     * 格式
     * {
     *    id:userid
     *    user:{
     *       name:username
     *       img:userimg
     *    }
     *    message[{
     *       context:msginfocontext
     *       date:datetime
     *    },{
     *       context:msginfocontext
     *       date:datetime
     *    }]
     *  }
     *
     *  获取所有和我方有关的其他接收方或发送方的所有用户ID
     *  遍历用户ID
     *  获取所有和该用户的聊天信息
     * */
    @Override
    public List<Object> getMsgList(String fromUserId, String toUserId) {
        List<Object> msgInfo = new ArrayList<>();
        //查询出发送时当前用户的所有接收用户的ID
        List<String> toUseridList = tMsgInfoMapper.getToUserId(fromUserId);
        List<String> fromUserList = tMsgInfoMapper.getFromUserId(fromUserId);
        //遍历list
        toUseridList.addAll(fromUserList);
        toUseridList.forEach((userid)->{
            if (userid != fromUserId){
                Map<String,Object> map = new HashMap<>();
                map.put("id",userid);
                TUser user = itUserService.getById(userid);
                Map<String,Object> userMap = new HashMap<>();
                userMap.put("username",user.getUsername());
                userMap.put("pictureUrl",user.getPictureUrl());
                map.put("user",userMap);
                List<TMsgInfo> tMsgInfoList = list(new QueryWrapper<TMsgInfo>()
                        .eq("from_user_id", fromUserId)
                        .eq("to_user_id", userid)
                        .or()
                        .eq("from_user_id", userid)
                        .eq("to_user_id", fromUserId)
                        .orderByAsc("create_time"));

                map.put("messages",tMsgInfoList);
                msgInfo.add(map);
            }
        });
        return msgInfo;
    }
}
