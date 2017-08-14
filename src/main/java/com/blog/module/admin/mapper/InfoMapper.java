package com.blog.module.admin.mapper;



import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.blog.module.model.Admin;
import org.apache.ibatis.annotations.Select;



public interface InfoMapper extends BaseMapper<Admin> {

    @Select({
        "select * from admin where username=#{username} limit 1 "
    })
    Admin findInfoByUserName();
}