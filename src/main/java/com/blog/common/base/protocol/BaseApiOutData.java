package com.blog.common.base.protocol;

import java.io.Serializable;

/**
 * 返回参数
 */
public class BaseApiOutData implements Serializable {
    private Integer status;//1：成功，0：失败

    private String message;//返回描述=

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
