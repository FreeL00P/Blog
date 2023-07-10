package com.freel00p.exception;

import com.freel00p.enums.AppHttpCodeEnum;

/**
 * SystemException
 *
 * @author fj
 * @since 2023/7/10 22:57
 */
public class SystemException extends RuntimeException{

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

}
