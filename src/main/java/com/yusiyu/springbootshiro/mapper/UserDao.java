package com.yusiyu.springbootshiro.mapper;

import com.yusiyu.springbootshiro.entity.Permission;
import com.yusiyu.springbootshiro.entity.Role;
import com.yusiyu.springbootshiro.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Mapper
@Repository
public interface UserDao {
    @Insert("insert into t_user values(#{id},#{username},#{password},#{salt})")
    @Options(useGeneratedKeys=true,keyColumn="id")
    public void register(User user);

    @Select("select *from t_user where username=#{username}")
    public User findUserByName(String username);

    //根据用户名查询所有角色
    //@Select("select*from t_role where id in(select roleid from t_user_role where userid=(select id from t_user where username=#{username}))")
//    @Select("select u.id uid,u.username uname,r.id rid,r.name rname " +
//            "from t_user u " +
//            "left join t_user_role ur on u.id=ur.userid " +
//            "left join t_role r on ur.roleid=r.id " +
//            "where u.username=#{username}")
    @Select("select *from t_user where username=#{username}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "roles",column = "id",javaType = java.util.List.class,many = @Many(select = "com.yusiyu.springbootshiro.mapper.RoleDao.findRoleByUserId"))
    })
    User findRolesByUserName(String username);
    //根据角色id查询权限集合
    @Select("select*from t_perms where id in (select permsid from t_role_perms where roleid=#{id})")
    List<Permission> findPermsByRoleId(String id);
}
