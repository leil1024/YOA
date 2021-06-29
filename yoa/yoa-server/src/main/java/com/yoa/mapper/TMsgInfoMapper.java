package com.yoa.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoa.entity.TMsgInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 消息表 Mapper 接口
 * </p>
 *
 * @author liulei
 * @since 2021-06-11
 */
@Mapper
@Repository
public interface TMsgInfoMapper extends BaseMapper<TMsgInfo> {

    /**
     * 获取接收者ID集合
     * */
    List<String> getToUserId(String fromUserId);

    /**
     * 获取发送者的ID
     * */
    List<String> getFromUserId(String toUserId);

    /**
     * 获取消息内容
     * */
    Map<String,Object> getContext(String objid);
}
