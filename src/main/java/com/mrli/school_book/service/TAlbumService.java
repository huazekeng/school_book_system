package com.mrli.school_book.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.school_book.entity.TAlbum;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mrli.school_book.form.SearchForm;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Harry
 * @since 2020-03-26
 */
public interface TAlbumService extends IService<TAlbum> {

    Page search(SearchForm form);
}
