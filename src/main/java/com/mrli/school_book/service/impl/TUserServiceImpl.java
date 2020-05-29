package com.mrli.school_book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mrli.school_book.entity.TUser;
import com.mrli.school_book.mapper.TUserMapper;
import com.mrli.school_book.service.TUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Harry
 * @since 2020-03-26
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements TUserService {

    @Override
    public TUser getByName(String username) {
        QueryWrapper<TUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return this.getOne(queryWrapper);
    }

    @Override
    public TUser getByEmail(String email) {
        QueryWrapper<TUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        return this.getOne(queryWrapper);    }
}
