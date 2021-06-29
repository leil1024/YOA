package com.yoa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoa.entity.TUser;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liulei
 * @since 2021-04-21
 */
@Mapper
@Repository
public interface TUserMapper extends BaseMapper<TUser> {

    List<TUser> getUserList();

    //查询消息记录不为0的相关用户
    List<TUser> unread(String activeUserId);

    //查询消息记录为0的其他用户
    List<TUser> read(List<String> userIdList);

    List<TUser> chatUserList();
}
