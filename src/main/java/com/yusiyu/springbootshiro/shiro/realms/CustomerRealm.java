package com.yusiyu.springbootshiro.shiro.realms;

import com.yusiyu.springbootshiro.entity.Permission;
import com.yusiyu.springbootshiro.entity.Role;
import com.yusiyu.springbootshiro.entity.User;
import com.yusiyu.springbootshiro.service.UserService;
import com.yusiyu.springbootshiro.utils.ApplicationContextUtils;
import com.yusiyu.springbootshiro.utils.MyByteSource;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.util.ObjectUtils;

import java.util.List;
@DependsOn("applicationContextUtils")
public class CustomerRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
    //UserService userService = (UserService) ApplicationContextUtils.getBean("userService");
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取用户认证的身份信息
        String primaryPrincipal = (String) principalCollection.getPrimaryPrincipal();
        //System.out.println("用户："+primaryPrincipal);
        User user = userService.findRolesByUserName(primaryPrincipal);
        System.out.println(user);
        if(!CollectionUtils.isEmpty(user.getRoles())){
            //说明有角色信息
            SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();
            user.getRoles().forEach(role -> {
                System.out.println(role.getName());
                simpleAuthorizationInfo.addRole(role.getName());
                List<Permission> perms = userService.findPermsByRoleId(role.getId());
                perms.forEach(permission -> {
                    simpleAuthorizationInfo.addStringPermission(permission.getUrl());
                });
            });
            return simpleAuthorizationInfo;
        }
        return null;
    }

    //角色
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String principal=(String) authenticationToken.getPrincipal();
        User user = userService.findUserByName(principal);
        if(!ObjectUtils.isEmpty(user)){
            return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(),    //账户名，密码，随机盐，域名
                    new MyByteSource(user.getSalt()),this.getName());
        }
//        SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo(principal,"123",this.getName());
//
//        if ("yusiyu".equals(principal)){
//            return simpleAuthenticationInfo;
//        }
        return null;
    }
}
