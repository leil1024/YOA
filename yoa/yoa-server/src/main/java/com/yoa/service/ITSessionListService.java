package com.yoa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yoa.entity.TSessionList;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会话列表 服务类
 * </p>
 *
 * @author liulei
 * @since 2021-06-11
 */
@Service
@Primary
public interface ITSessionListService extends IService<TSessionList> {

}
