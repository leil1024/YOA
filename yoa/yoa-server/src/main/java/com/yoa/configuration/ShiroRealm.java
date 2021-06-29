package com.yoa.configuration;

import com.yoa.entity.TUser;
import com.yoa.service.impl.TUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    TUserServiceImpl tUserService;

    /**
     * 授权
     * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }


    /**
     * 认证
     * realm会获取用户提交的身份信息，和数据库中的数据进行比对
     * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //获取用户的身份信息,用户输入的用户名
        String userPrincipal = (String) token.getPrincipal();
        log.info("To get the user name:" + userPrincipal);

        //根据用户身份在数据库信息获取数据
        TUser tUser = tUserService.getUser(userPrincipal);
        /**当用户名不存在时，自定义shiro异常返回信息，通过全局异常捕获类，向前端发送异常信息
         * 全局异常捕获无法获取doGetAuthenticationInfo里的异常？？？？
         * 此处异常暂更改到登陆方法LoginService方法中抛出
         * */
        if (tUser == null){
            throw new UnknownAccountException("用户不存在");
        }

        /**如果用户名存在，封装用户的信息：正确的用户信息、用户密码、realm名称*/
        return new SimpleAuthenticationInfo(tUser,tUser.getPassword(),this.getName());
    }
}
