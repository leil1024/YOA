package com.yoa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoa.entity.TPosition;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liulei
 * @since 2021-05-07
 */
@Mapper
@Repository
public interface TPositionMapper extends BaseMapper<TPosition> {

}
