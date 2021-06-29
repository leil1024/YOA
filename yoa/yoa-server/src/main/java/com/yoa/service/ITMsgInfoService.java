package com.yoa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.org.apache.xpath.internal.objects.XString;
import com.yoa.entity.TMsgInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 消息表 服务类
 * </p>
 *
 * @author liulei
 * @since 2021-06-11
 */
public interface ITMsgInfoService extends IService<TMsgInfo> {

    //获取聊天记录
    List<Object> getMsgList(String fromUserId, String toUserId);
}
