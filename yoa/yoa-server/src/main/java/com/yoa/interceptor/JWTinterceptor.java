package com.yoa.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.yoa.uitl.JwtUitl;
import com.yoa.uitl.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JWTinterceptor implements HandlerInterceptor {
    /**
     * JWT token拦截器，验证token是否正确
     * token验证正确放行
     * token错误抛出异常
     * interecption拦截器在全局异常处理之前执行，无法直接在全局异常处理中拦截异常抛出
     * 请求执行流程：filter -> interceptor -> controllerAdvice -> aspect  -> controller
     * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //前端请求是将token放在请求头，在header中获取token信息
        String token = request.getHeader("token");
        try {
            JwtUitl.DecryptToken(token);
            return true;
        }catch (Exception e){
            log.info("Exception caught:" + e.getMessage());
        }
        Result result = Result.fail(400,"token无效");
        String jsonString = JSONObject.toJSONString(result);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(jsonString);
        return false;
    }
}
