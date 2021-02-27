package com.yusiyu.springbootshiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("order")
//@RequiresPermissions("user:update:01")
public class OrderController {

    @RequestMapping("save")
    //@RequiresRoles(value = {"user","admin"})   ///同时具有相同角色的时候可以访问
    @RequiresPermissions("user:delete:01")
    public String save(){
        System.out.println("进入方法");
//        Subject subject= SecurityUtils.getSubject();
//        //代码方式
//        if(subject.hasRole("admin")){
//            System.out.println("保存订单");
//        }else{
//            System.out.println("无权访问");
//        }
        return "redirect:/index.jsp";
    }
}
