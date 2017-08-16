package com.blog.module.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.blog.module.model.Admin;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

/**
 * Created by danielho on 2017/8/14.
 */
@Mapper
public interface AdminMapper  extends BaseMapper<Admin> {


    /**
     * 通过用户名和密码，获取账户信息
     * @param map
     * @return
     */
    Admin getAdminByParas(HashMap<String,Object> map);

    /**
     * 更改账户信息
     * @param admin
     */
    void updateAdminInfo(Admin admin);

    /**
     * 新增账户信息
     * @param admin
     */
    void addAdminInfo(Admin admin);
}
