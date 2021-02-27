package com.yusiyu.springbootshiro.service.Impl;

import com.yusiyu.springbootshiro.entity.Permission;
import com.yusiyu.springbootshiro.entity.Role;
import com.yusiyu.springbootshiro.entity.User;
import com.yusiyu.springbootshiro.mapper.UserDao;
import com.yusiyu.springbootshiro.service.UserService;
import com.yusiyu.springbootshiro.utils.SaltUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Override
    public void register(User user) {
        String salt=SaltUtils.getSalt(8);
        //将随机盐保存到数据
        user.setSalt(salt);
        //明文密码进行md5+salt+hash散列
        Md5Hash md5Hash=new Md5Hash(user.getPassword(),salt,1024);
        user.setPassword(md5Hash.toHex());

        userDao.register(user);
    }

    @Override
    public User findUserByName(String username) {
        User user = userDao.findUserByName(username);
        return user;
    }

    @Override
    public User findRolesByUserName(String username) {
        return userDao.findRolesByUserName(username);
    }

    @Override
    public List<Permission> findPermsByRoleId(String id) {
        return userDao.findPermsByRoleId(id);
    }
}
