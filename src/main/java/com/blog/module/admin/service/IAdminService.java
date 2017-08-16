package com.blog.module.admin.service;

import com.blog.module.model.Admin;

/**
 * Created by danielho on 2017/8/15.
 */
public interface IAdminService {

    /**
     * 获取账户信息
     * @param username
     * @return
     */
    Admin getAdminInfo(String username);

    /**
     * 登录信息
     * @param username
     * @param password
     * @return
     */
    Admin login(String username,String password);

    void updateAdmin(Admin admin);

    void addAdmin(Admin admin);
}
