package com.mrli.school_book.controller.api;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.school_book.dto.admin.RecordToVo;
import com.mrli.school_book.entity.TAlbum;
import com.mrli.school_book.entity.TBorrowrecord;
import com.mrli.school_book.entity.TReader;
import com.mrli.school_book.security.JwtConfig;
import com.mrli.school_book.service.TAlbumService;
import com.mrli.school_book.service.TBorrowrecordService;
import com.mrli.school_book.util.RedisUtils;
import com.mrli.school_book.vo.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  借阅记录控制器
 * </p>
 *
 * @author Harry
 * @since 2020-03-26
 */
@RestController
@RequestMapping("/api/brecord")
public class BorrowrecordController {

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private TBorrowrecordService borrowrecordService;

    @Autowired
    private RecordToVo recordToVo;

    @Autowired
    private TAlbumService bookService;

    /**
     * 我的借阅列表
     * @param request
     * @param currPage
     * @param limit
     * @param query
     * @return
     */
    @GetMapping("/{currPage}/{limit}")
    public R list(HttpServletRequest request, @PathVariable("currPage")Integer currPage, @PathVariable("limit")Integer limit , @RequestParam(value = "query",required = false)String query) {

        Page<TBorrowrecord> page = new Page<>();
        page.setCurrent(currPage);
        page.setSize(limit);

        QueryWrapper<TBorrowrecord> queryWrapper = new QueryWrapper<>();

        String authHeader = request.getHeader(jwtConfig.getHeader());
        String authToken = authHeader.substring(jwtConfig.getTokenHead().length() + 1);
        TReader user = (TReader) redisUtils.get(authToken);
        queryWrapper.eq("raccount", user.getAccount());
        queryWrapper.orderByDesc("gmt_create");
        this.borrowrecordService.page(page, queryWrapper);

        return R.ok().data("total", page.getTotal()).data("rows", recordToVo.toVo(page.getRecords()));
    }

    /**
     * 我的被借列表
     * @param request
     * @param currPage
     * @param limit
     * @param query
     * @return
     */
    @GetMapping("/beb/{currPage}/{limit}")
    public R beborrow(HttpServletRequest request, @PathVariable("currPage")Integer currPage, @PathVariable("limit")Integer limit , @RequestParam(value = "query",required = false)String query) {

        // 设置分页信息
        Page<TBorrowrecord> page = new Page<>();
        page.setCurrent(currPage);
        page.setSize(limit);

        // 获取请求头token
        String authHeader = request.getHeader(jwtConfig.getHeader());
        String authToken = authHeader.substring(jwtConfig.getTokenHead().length() + 1);
        TReader user = (TReader) redisUtils.get(authToken);

        // 查询自己发布的书籍
        QueryWrapper<TAlbum> albumQueryWrapper = new QueryWrapper<>();
        albumQueryWrapper.eq("username", user.getAccount());
        List<TAlbum> myBooks = bookService.list(albumQueryWrapper);
        List<Integer> bookIds = new ArrayList<>();

        myBooks.forEach(item -> {
            bookIds.add(item.getAid());
        });

        // 根据自己的书籍查找借阅
        QueryWrapper<TBorrowrecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("aid", bookIds);

        queryWrapper.orderByDesc("gmt_create");

        // 我的所有被借
        IPage<TBorrowrecord> page1 = this.borrowrecordService.page(page, queryWrapper);
        return R.ok().data("total", page.getTotal()).data("rows", recordToVo.toVo(page.getRecords()));
    }
}

