package com.mrli.school_book.security;

import com.mrli.school_book.constant.ResultCodeEnum;
import com.mrli.school_book.util.ResponseUtil;
import com.mrli.school_book.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Spring security登录失败处理类
 *  *
 *  *
 */
@Component
@Slf4j
public class JwtAuthenticationFailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException exception) throws IOException, ServletException {
        log.info("登录失败");
        R r =  R.error().setResult(ResultCodeEnum.LOGIN_ERROR);
        if (exception instanceof LockedException) {
            r.setMessage("账户被锁定，请联系管理员!");
        } else if (exception instanceof CredentialsExpiredException) {
            r.setMessage("密码过期，请联系管理员!");
        } else if (exception instanceof AccountExpiredException) {
            r.setMessage("账户过期，请联系管理员!");
        } else if (exception instanceof DisabledException) {
            r.setMessage("账户被禁用，请联系管理员!");
        } else if (exception instanceof BadCredentialsException) {
            r.setMessage("用户名或者密码输入错误，请重新输入!");
        }

        exception.printStackTrace();
        ResponseUtil.ajaxResponse(httpServletResponse,r);
    }
}
