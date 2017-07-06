package com.blog.common.base.protocol;

import java.io.Serializable;

/**
 * 基础返回类
 */
public class BaseRsOutData implements Serializable {
    private String recode = ReturnCode.cSucc;//返回代码
    private String rmsg="";//错误信息代码

    public String getRecode() {
        return recode;
    }

    public void setRecode(String recode) {
        this.recode = recode;
    }

    public String getRmsg() {
        return rmsg;
    }

    public void setRmsg(String rmsg) {
        this.rmsg = rmsg;
    }
}
