package com.yoa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yoa.entity.TPosition;
import com.yoa.uitl.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liulei
 * @since 2021-05-07
 */
public interface ITPositionService extends IService<TPosition> {
    /**
     * 职位添加
     * @param posName
     * */
    Result addposition(String posName);

    /**
     * 职位删除
     * @param
     * */
    Result delposition(String id);

    /**
     * 职位更新
     * */
    Result upposition(TPosition tPosition);

}
