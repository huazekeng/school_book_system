package com.mrli.school_book.security;

import com.mrli.school_book.entity.TUser;
import com.mrli.school_book.service.TUserService;
import com.mrli.school_book.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户登录逻辑
 */
@Component
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TUserService tUserService;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

        TUser user = tUserService.getByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("用户名不存在"));
        }

        JwtUser JwtUser = new JwtUser(username,user.getPassword(),list);

        JwtUser.setIsEnable(user.getCondi() == 0 ? false : true);

        return JwtUser;
    }
}
