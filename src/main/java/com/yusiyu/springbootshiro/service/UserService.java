package com.yusiyu.springbootshiro.service;

import com.yusiyu.springbootshiro.entity.Permission;
import com.yusiyu.springbootshiro.entity.Role;
import com.yusiyu.springbootshiro.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    public void register(User user);

    public User findUserByName(String username);

    //根据用户名查询所有角色
    User findRolesByUserName(String username);
    //根据角色id查询权限集合
    List<Permission> findPermsByRoleId(String id);
}
