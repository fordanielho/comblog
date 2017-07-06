package com.blog.common.base.protocol;



import java.io.Serializable;

public class BaseApiInData implements Serializable {

    private String body; //请求JSONString

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

