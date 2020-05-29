package com.mrli.school_book.security;


import com.mrli.school_book.constant.ResultCodeEnum;
import com.mrli.school_book.util.ResponseUtil;
import com.mrli.school_book.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Spring security其他异常处理类,比如请求路径不存在等，
 * 如果不配置此类，则Spring security默认会跳转到登录页面
 */
@Component
@Slf4j
public class JwtAuthenticationException implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        log.info("AuthenticationEntryPoint检测到异常:"+e);
        ResponseUtil.ajaxResponse(httpServletResponse,R.setResult(ResultCodeEnum.USER_NOT_LOGIN));
    }
}

