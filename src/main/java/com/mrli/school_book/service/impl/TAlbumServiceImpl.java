package com.mrli.school_book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.school_book.entity.TAlbum;
import com.mrli.school_book.form.SearchForm;
import com.mrli.school_book.mapper.TAlbumMapper;
import com.mrli.school_book.service.TAlbumService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
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
public class TAlbumServiceImpl extends ServiceImpl<TAlbumMapper, TAlbum> implements TAlbumService {

    @Override
    public Page search( SearchForm form) {
        Page<TAlbum> page = new Page<>();
        page.setCurrent(form.getCurrPage());
        page.setSize(form.getPageSize());

        QueryWrapper<TAlbum> queryWrapper = new QueryWrapper<>();

        if(StringUtils.isNoneBlank(form.getKeyword())) {
            queryWrapper.like("title", form.getKeyword());
        }

        if(form.getDescCol() == null || "".equals(form.getDescCol())) {
            queryWrapper.orderByDesc("gmt_create");
        } else {
            if(form.getDescType().intValue() == 1 ) {
                queryWrapper.orderByDesc(form.getDescCol());
            } else {
                queryWrapper.orderByAsc(form.getDescCol());
            }
        }
        this.page(page, queryWrapper);
        return  page;
    }
}
