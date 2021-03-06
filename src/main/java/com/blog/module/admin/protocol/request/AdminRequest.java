package com.blog.module.admin.protocol.request;

import java.io.Serializable;

/**
 * Created by danielho on 2017/8/15.
 */
public class AdminRequest implements Serializable {
    /**
     * 用户名
     */
    private String username;
    /**
     * md5加密后的密码
     */
    private String password;
    /**
     * 邮箱,默认为#
     */
    private String email;
    /**
     * github地址，默认为#
     */
    private String github;
    /**
     * twitter地址，默认为#
     */
    private String twitter;
    /**
     * 简历的markdown文本，为了admin管理时能够回显
     */
    private String md;
    /**
     * 简历的html文本
     */
    private String resume;
    /**
     * 头像地址
     */
    private String avatar;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getMd() {
        return md;
    }

    public void setMd(String md) {
        this.md = md;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
