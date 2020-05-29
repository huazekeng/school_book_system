package com.mrli.school_book.exception;

import com.mrli.school_book.constant.ResultCodeEnum;
import org.springframework.security.core.AuthenticationException;

/**
 * 用戶未登錄異常
 */

public class UserNotActiveException extends AuthenticationException {

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public UserNotActiveException(ResultCodeEnum codeEnum){
        super(codeEnum.getMessage());
        this.code = codeEnum.getCode();
    }


}
