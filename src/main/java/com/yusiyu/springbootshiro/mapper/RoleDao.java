package com.yusiyu.springbootshiro.mapper;

import com.yusiyu.springbootshiro.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleDao {
    @Select("select * from t_role where id in (select roleid from t_user_role where userid=#{id})")
    List<Role> findRoleByUserId(String id);
}
