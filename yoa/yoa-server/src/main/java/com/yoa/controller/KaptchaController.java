package com.yoa.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 验证码请求
 * */

@RestController
@Slf4j
public class KaptchaController {

    @Autowired
    DefaultKaptcha defaultKaptcha;

    @Autowired
    RedisTemplate redisTemplate;

    @CrossOrigin
    @GetMapping("/getimg")
    public void kaptcha(HttpServletResponse response) throws IOException {
        //设置浏览器数据缓存有效性
        response.setDateHeader("Expires",0);
        //设置浏览器数据缓存(不缓存，每次都去请求新的数据)
        response.setHeader("Cache-Control","no-store, no-cache, must-revalidate");
        //针对于IE5的防缓存声明
        response.addHeader("Cache-Control","post-check=0,pre-check=0");
        //
        response.setHeader("Pragme","no-cache");
        //设置返回格式
        response.setContentType("image/jpeg");

        //生成验证码文字
        String kaptchaText = defaultKaptcha.createText();
        log.info("Generate verification code：" + kaptchaText);


        //放入session是get和set获取的不是同一个session，导致get结果为null
        //验证码放入redis
        //验证码作为key，防止冲突
        redisTemplate.opsForValue().set(kaptchaText,kaptchaText);

        //根据文字生成图片
        BufferedImage image = defaultKaptcha.createImage(kaptchaText);
        ImageIO.write(image, "jpg", response.getOutputStream());

    }
}
