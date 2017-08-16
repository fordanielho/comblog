package com.blog.module.admin.controller;

import com.blog.common.base.BaseController;
import com.blog.common.base.constants.GlobalConstants;
import com.blog.common.base.protocol.BaseResponse;
import com.blog.common.base.protocol.ReturnCode;
import com.blog.common.base.util.PasswordUtil;
import com.blog.module.admin.protocol.request.AdminRequest;
import com.blog.module.admin.service.IAdminService;
import com.blog.module.model.Admin;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by danielho on 2017/8/14.
 */
@RestController
@RequestMapping("/admin")
public class AdminController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    IAdminService adminService;


    @RequestMapping("/login")
    public BaseResponse login(@RequestBody AdminRequest request){
        BaseResponse response = new BaseResponse();
        try{
            if(StringUtils.isNotBlank(request.getUsername())&&StringUtils.isNotBlank(request.getPassword())){
                String encryptPassword = PasswordUtil.encryptPWMD5(request.getPassword());
                Admin admin = adminService.login(request.getUsername(),encryptPassword);
                if(admin !=null){
                    response.setRecode(ReturnCode.cSucc);
                    response.setReturnMsg("登录成功");
                }else {
                    response.setRecode(ReturnCode.cRmsg);
                    response.setReturnMsg("不存在该账号用户，请联系管理员");
                }

            }else {
                response.setRecode(ReturnCode.cParaError);
                response.setReturnMsg("参数不完整");
            }
        }catch (Exception e){
            //e.printStackTrace();
            logger.error("登录出错", e);
            response.setRecode(ReturnCode.cError);
            response.setReturnMsg("登录出错");
        }
        return response;
    }

    @Transactional
    @RequestMapping("/updateInfo")
    public BaseResponse updateInfo(@RequestBody AdminRequest request){
        BaseResponse response = new BaseResponse();
        try{

            if(StringUtils.isNotBlank(request.getUsername())){
                Admin admin = adminService.getAdminInfo(request.getUsername());
                if(admin==null){
                    response.setRecode(ReturnCode.cRmsg);
                    response.setReturnMsg("不存在该账号用户，请联系管理员");
                    return response;
                }
                if(StringUtils.isNotBlank(request.getPassword())){
                    admin.setPassword(PasswordUtil.encryptPWMD5(request.getPassword()));
                }
                if(StringUtils.isNotBlank(request.getEmail())){
                    admin.setEmail(request.getEmail());
                }
                if(StringUtils.isNotBlank(request.getGithub())){
                    admin.setGithub(request.getGithub());
                }

                if(StringUtils.isNotBlank(request.getTwitter())){
                    admin.setTwitter(request.getTwitter());
                }
                if(StringUtils.isNotBlank(request.getMd())){
                    admin.setMd(request.getMd());
                }
                if(StringUtils.isNotBlank(request.getResume())){
                    admin.setResume(request.getResume());
                }
                if(StringUtils.isNotBlank(request.getAvatar())){
                    admin.setAvatar(request.getAvatar());
                }
                adminService.updateAdmin(admin);
            }else {
                response.setRecode(ReturnCode.cParaError);
                response.setReturnMsg("参数不完整");
            }
        }catch (Exception e){
            //e.printStackTrace();
            logger.error("更新账户信息出错", e);
            response.setRecode(ReturnCode.cError);
            response.setReturnMsg("更新账户信息出错");
        }
        return response;
    }

    @Transactional
    @RequestMapping("/add")
    public BaseResponse add(@RequestBody AdminRequest request){
        BaseResponse response = new BaseResponse();
        try{
            if(StringUtils.isNotBlank(request.getUsername())){
                Admin admin = adminService.getAdminInfo(request.getUsername());
                if(admin!=null){
                    response.setRecode(ReturnCode.cRmsg);
                    response.setReturnMsg("已存在该账号用户，请查证");
                    return response;
                }
                admin = new Admin();
                admin.setUsername(request.getUsername());
                admin.setPassword(PasswordUtil.encryptPWMD5(GlobalConstants.INITIAL_PASSWORD));
                adminService.addAdmin(admin);
            }else {
                response.setRecode(ReturnCode.cParaError);
                response.setReturnMsg("参数不完整");
            }

        }catch (Exception e){
            //e.printStackTrace();
            logger.error("新增账户出错", e);
            response.setRecode(ReturnCode.cError);
            response.setReturnMsg("新增账户出错");
        }
        return response;
    }


}
