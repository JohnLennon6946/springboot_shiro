package com.yusiyu.springbootshiro.controller;

import com.yusiyu.springbootshiro.entity.Role;
import com.yusiyu.springbootshiro.entity.User;
import com.yusiyu.springbootshiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("login")
    public String login(String username,String password){
        //获取主体对象
        Subject subject= SecurityUtils.getSubject();
        try{
            subject.login(new UsernamePasswordToken(username,password));
            return "redirect:/index.jsp";
        }catch (UnknownAccountException e){
            e.printStackTrace();
            System.out.println("用户名错误");
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            System.out.println("密码错误");
        }
        return "redirect:/login.jsp";
    }

    @RequestMapping("logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login.jsp";
    }

    @RequestMapping("register")
    public String register(User user){
        System.out.println("执行了该方法");
        try {
            userService.register(user);
            System.out.println("注册成功");
            return "redirect:/login.jsp";
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/register.jsp";
        }
    }

    @RequestMapping("find")
    public String find(String username){
        User user = userService.findUserByName(username);
        return null;
    }

    @RequestMapping("role")
    @ResponseBody
    public String findRole(){
        User user = userService.findRolesByUserName("zhang");
        System.out.println(user.getRoles());
        user.getRoles().forEach(role -> {
            System.out.println(role.getName());
        });
        return "kkk";
    }
}
