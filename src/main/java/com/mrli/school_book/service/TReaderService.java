package com.mrli.school_book.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.school_book.entity.TReader;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mrli.school_book.form.SearchForm;
import com.mrli.school_book.vo.R;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Harry
 * @since 2020-03-26
 */
public interface TReaderService extends IService<TReader> {

    TReader getByAccount(String username);

    /**
     * 归还书籍
     * @param account
     * @param bid
     */
    void reabck(String account, Integer bid);

    R borrow(TReader user, Integer id);
}
