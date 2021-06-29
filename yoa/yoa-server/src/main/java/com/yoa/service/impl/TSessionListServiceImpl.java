package com.yoa.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoa.entity.TSessionList;
import com.yoa.mapper.TSessionListMapper;
import com.yoa.service.ITSessionListService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会话列表 服务实现类
 * </p>
 *
 * @author liulei
 * @since 2021-06-11
 */
@Service
public class TSessionListServiceImpl extends ServiceImpl<TSessionListMapper, TSessionList> implements ITSessionListService {

}
