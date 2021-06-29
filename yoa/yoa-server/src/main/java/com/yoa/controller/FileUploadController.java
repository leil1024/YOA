package com.yoa.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yoa.entity.TUser;
import com.yoa.service.ITUserService;
import com.yoa.uitl.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.Cacheable;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件上传
 * */
@CrossOrigin
@RestController
public class FileUploadController {

    @Autowired
    ITUserService itUserService;

    @PostMapping("/upload")
    public Result fileUpload(@RequestParam("file")MultipartFile file,@RequestParam("userid")String userid,@RequestParam("ispicture") String ispicture){
        if (file.isEmpty()){
            Result.fail(400,"请选择文件内容");
        }
        try {
            //普通文件存储路径
            String filePath = "E:/FTP/file";
            //图片存储内容
            String picturePath = "E:/FTP/picture";
            byte[] bytes = file.getBytes();
            Path path = null;
            //如果ispicture参数为1，这判断此张图片为头像，否则为普通文件
            if (ispicture.equals("1")){
                path = Paths.get(picturePath + '/' + userid + '_' + file.getOriginalFilename());
                String imgUrl = "http://192.168.14.1:9001/picture/" + userid + '_' + file.getOriginalFilename();
                Files.write(path,bytes);
                itUserService.update(new UpdateWrapper<TUser>().set("picture_url",imgUrl).eq("id",userid));
                return Result.succ("上传成功",imgUrl);
            }else {
                path = Paths.get(filePath + '/' + file.getOriginalFilename());
                Files.write(path,bytes);
                return Result.succ("上传成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(400,"文件不符合规范");
        }
    }
}
