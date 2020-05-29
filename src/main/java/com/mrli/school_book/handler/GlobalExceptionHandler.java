package com.mrli.school_book.handler;


import com.mrli.school_book.exception.BookException;
import com.mrli.school_book.util.ExceptionUtil;
import com.mrli.school_book.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R exception(Exception e){
        e.printStackTrace();
        return R.error();
    }

    @ExceptionHandler(BookException.class)
    @ResponseBody
    public R error(BookException e){
        log.error(ExceptionUtil.getMessage(e));
        return R.error().code(e.getCode()).message(e.getMessage());
    }


}
