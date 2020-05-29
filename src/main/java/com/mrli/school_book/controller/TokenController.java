package com.mrli.school_book.controller;

import com.mrli.school_book.constant.ResultCodeEnum;
import com.mrli.school_book.exception.BookException;
import com.mrli.school_book.security.JwtConfig;
import com.mrli.school_book.security.JwtUser;
import com.mrli.school_book.security.JwtUtils;
import com.mrli.school_book.util.RedisUtils;
import com.mrli.school_book.vo.R;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 *
 * @author HZK
 * @since 2020-01-19
 */
@RestController
@RequestMapping("/token")
@Api(tags = "Token控制器")
@Slf4j
public class TokenController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private JwtConfig jwtConfig;

    @ApiOperation("刷新Token")
    @GetMapping("/refresh")
    public R refresh(@ApiParam(name="refresh_token",value = "刷新token")String refresh_token, @ApiParam(name="token",value = "token")String token){
        Claims claims = JwtUtils.parseJWT(refresh_token);
        if(JwtUtils.isTokenExpired(claims)){//token过期
            log.info("token过期" + refresh_token);
            // 從redis 中移除 token
            redisUtils.remove(refresh_token);
            redisUtils.remove(token);
            throw new BookException(ResultCodeEnum.REFRESH_TOKEN_EXPIRE);
        }
        token = token.substring(jwtConfig.getTokenHead().length() + 1);
        String userId = redisUtils.get(refresh_token).toString();
        String newToken = jwtUtils.createJwtToken(userId,jwtConfig.getExpiresSecond());
        JwtUser jwtUser = (JwtUser)redisUtils.get(token);

        // 保存新Token ，刪除舊Token
        redisUtils.set(newToken, jwtUser);
        redisUtils.remove(token);
        return R.ok().data("token",jwtConfig.getTokenHead() + " " + newToken);
    }
}
