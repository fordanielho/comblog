package com.blog.module.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.blog.module.model.Admin;
import org.apache.ibatis.annotations.Select;

/**
 * Created by danielho on 2017/8/14.
 */
public interface AdminMapper  extends BaseMapper<Admin> {

    @Select({
            "select * from admin where username=#{username} limit 1 "
    })
    Admin getAdminByParas();
}
