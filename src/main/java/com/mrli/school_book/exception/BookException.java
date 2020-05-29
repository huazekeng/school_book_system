package com.mrli.school_book.exception;

import com.mrli.school_book.constant.ResultCodeEnum;
import lombok.Data;

@Data
public class BookException extends RuntimeException {

    private int code;

    public BookException(int code, String message){
        super(message);
        this.code = code;
    }

    public BookException(ResultCodeEnum rce){
        super(rce.getMessage());
        this.code = rce.getCode();
    }

    @Override
    public String toString() {
        return "SecondShopException{" +
                "code=" + code +
                "message=" + this.getMessage() +
                '}';
    }
}
