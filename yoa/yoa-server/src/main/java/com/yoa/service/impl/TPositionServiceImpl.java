package com.yoa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoa.entity.TPosition;
import com.yoa.mapper.TPositionMapper;
import com.yoa.service.ITPositionService;
import com.yoa.uitl.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liulei
 * @since 2021-05-07
 */
@Service
public class TPositionServiceImpl extends ServiceImpl<TPositionMapper, TPosition> implements ITPositionService {

    @Autowired
    TPositionMapper tPositionMapper;


    /**
     * 根据执行结果返回对应信息
     * @param result
     * @param message
     * */
    public String getResult(boolean result,String message){
        String returnMessage = "";
        try {
            if(result){
                returnMessage = message;
            }
        }catch (Exception e){
            returnMessage = e.getMessage();
        }
        return returnMessage;
    }


    /**
     * 职位添加
     * @param posName
     * */
    @Override
    public Result addposition(String posName) {
        String id = UUID.randomUUID().toString().replaceAll("-","");
        boolean result = save(new TPosition(id,posName,null,1));
        String message = getResult(result,"添加成功");
        return Result.succ(message);
    }


    /**
     * 删除
     * @param id
     * */
    @Override
    public Result delposition(String id) {
        boolean result = removeById(id);
        String message = getResult(result,"删除成功");
        return Result.succ(message);
    }


    /**
     * 职位更新
     * @param tPosition
     * */
    @Override
    public Result upposition(TPosition tPosition) {
        boolean result = updateById(tPosition);
        String message = getResult(result,"更新成功");
        return Result.succ(message);
    }

}
