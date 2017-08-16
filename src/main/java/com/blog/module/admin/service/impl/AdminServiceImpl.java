package com.blog.module.admin.service.impl;


import com.blog.module.admin.mapper.AdminMapper;
import com.blog.module.admin.service.IAdminService;
import com.blog.module.model.Admin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by danielho on 2017/8/15.
 */
@Service
public class AdminServiceImpl implements IAdminService {


    private static Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    AdminMapper mapper;

    /**
     * 获取账户信息
     * @param username
     * @return
     */
    @Override
    public Admin getAdminInfo(String username) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("username",username);
        Admin admin = mapper.getAdminByParas(map);
        return admin;
    }

    @Override
    public Admin login(String username, String password) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("username",username);
        map.put("password",password);
        Admin admin = mapper.getAdminByParas(map);
        return admin;
    }

    @Override
    public void updateAdmin(Admin admin) {
        mapper.updateAdminInfo(admin);
    }

    @Override
    public void addAdmin(Admin admin) {
        mapper.addAdminInfo(admin);
    }


}
