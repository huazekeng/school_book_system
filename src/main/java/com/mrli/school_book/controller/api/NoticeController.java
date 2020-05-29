package com.mrli.school_book.controller.api;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.school_book.entity.TAlbum;
import com.mrli.school_book.entity.TNotice;
import com.mrli.school_book.entity.TReader;
import com.mrli.school_book.security.JwtConfig;
import com.mrli.school_book.service.TNoticeService;
import com.mrli.school_book.util.RedisUtils;
import com.mrli.school_book.vo.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  通告控制器
 * </p>
 *
 * @author Harry
 * @since 2020-03-26
 */
@RestController
@RequestMapping("/api/notice")
public class NoticeController {



    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private TNoticeService noticeService;

    /**
     * 书籍列表
     * @param currPage
     * @param limit
     * @param query
     * @return
     */
    @GetMapping("/{currPage}/{limit}")
    public R list(@PathVariable("currPage")Integer currPage, @PathVariable("limit")Integer limit , @RequestParam(value = "query",required = false)String query
            , @RequestParam(value = "type",required = false)Integer type
            , HttpServletRequest request) {

        Page<TNotice> page = new Page<>();

        page.setCurrent(currPage);
        page.setSize(limit);
        QueryWrapper<TNotice> queryWrapper = new QueryWrapper<>();

        if(type != null && type == 2) {
            String authHeader = request.getHeader(jwtConfig.getHeader());
            String authToken = authHeader.substring(jwtConfig.getTokenHead().length() + 1);
            TReader user = (TReader) redisUtils.get(authToken);
            queryWrapper.eq("username", user.getAccount());
        }

        queryWrapper.orderByDesc("gmt_create");
        this.noticeService.page(page, queryWrapper);

        return R.ok().data("total", page.getTotal()).data("rows", page.getRecords());
    }


}

