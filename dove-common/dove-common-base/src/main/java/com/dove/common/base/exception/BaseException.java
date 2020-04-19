package com.dove.common.base.exception;


import com.dove.common.base.enm.SysErrorEnum;

public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private Integer code;
    private String message;

    public BaseException(String message) {
        this(SysErrorEnum._ERROR.getCode(), message);
    }

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;

    }

    public BaseException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
