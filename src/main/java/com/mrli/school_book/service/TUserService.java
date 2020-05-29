package com.mrli.school_book.service;

import com.mrli.school_book.entity.TUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Harry
 * @since 2020-03-26
 */
public interface TUserService extends IService<TUser> {

    /**
     * 根据用户名获取
     * @param username
     */
    TUser getByName(String username);

    TUser getByEmail(String email);
}
