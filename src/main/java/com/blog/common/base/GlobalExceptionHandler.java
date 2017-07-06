package com.blog.common.base;


import com.blog.common.base.protocol.BaseResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * 为BaseException异常创建对应的处理
 */

public class GlobalExceptionHandler {

    @ExceptionHandler(value = BaseException.class)
    public BaseResponse jsonErrorHandler(HttpServletRequest req, BaseException e) throws Exception {
        BaseResponse r = new BaseResponse();
        r.setReturnMsg(e.getMessage());
        r.setRecode(Global.RETURN_CODE_SERVER_ERR);
        return r;
    }

    @ExceptionHandler(value = Exception.class)
    public BaseResponse defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        BaseResponse r = new BaseResponse();
        r.setReturnMsg(e.getMessage());
        r.setRecode(Global.RETURN_CODE_SERVER_ERR);
        return r;
    }
}
