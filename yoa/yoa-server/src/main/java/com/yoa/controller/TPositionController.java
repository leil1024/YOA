package com.yoa.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yoa.entity.TPosition;
import com.yoa.service.ITPositionService;
import com.yoa.service.ITSessionListService;
import com.yoa.uitl.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liulei
 * @since 2021-05-07
 */
@CrossOrigin
@RestController
public class TPositionController{

    @Autowired
    ITPositionService itPositionService;

    @Autowired
    ITSessionListService itSessionListService;

    /**
     *返回所有职位信息
     * 使用mybatis-plus分页插件
     * */
    @RequestMapping("/position")
    public Result positionList(@RequestParam(defaultValue = "1") Integer PageNumber){
        Page page = new Page(PageNumber, 5);
        Page PositionData = itPositionService.page(page, new QueryWrapper<TPosition>().orderByDesc("create_time"));
        return Result.succ(null,PositionData);
    }

    /**
     * 返回所有职位信息
     * */
    @GetMapping("/enablePos")
    public Result allEnablePosList(){
        List<TPosition> tPositions = itPositionService.list(new QueryWrapper<TPosition>().eq("enable", 1));
        return Result.succ(null,tPositions);
    }

    /**
     * 添加职位信息
     * @param posName
     * */
//    @PostMapping("/addposition/{posName}") ----乱码，未解决
    @PostMapping("/addposition")
    public Result AddPosition(String posName){
        return itPositionService.addposition(posName);
    }


    /**
     * 删除职位信息
     * @param id
     * */
    @PostMapping("/delposition")
    public Result DelPosition(String id){
        return itPositionService.delposition(id);
    }

    /**
     *更改职位信息
     * */
    @PostMapping("/upposition")
    public Result UpPosition(TPosition tPosition){
//        System.out.println(tPosition.getPosName());
        return itPositionService.upposition(tPosition);
    }
}
