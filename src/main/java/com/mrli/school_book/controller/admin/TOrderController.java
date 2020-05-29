package com.mrli.school_book.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.school_book.entity.TOrder;
import com.mrli.school_book.entity.TReader;
import com.mrli.school_book.security.JwtConfig;
import com.mrli.school_book.service.TOrderService;
import com.mrli.school_book.util.RedisUtils;
import com.mrli.school_book.vo.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  订单控制器
 * </p>
 *
 * @author Harry
 * @since 2020-04-06
 */
@RestController
@RequestMapping("/admin/order")
public class TOrderController {

    @Autowired
    TOrderService orderService;

    /**
     * 书籍列表
     * @param currPage
     * @param limit
     * @param query
     * @return
     */
    @GetMapping("/{currPage}/{limit}")
    public R list(@PathVariable("currPage")Integer currPage, @PathVariable("limit")Integer limit , @RequestParam(value = "query",required = false)String query) {

        Page<TOrder> page = new Page<>();

        page.setCurrent(currPage);
        page.setSize(limit);
        QueryWrapper<TOrder> queryWrapper = new QueryWrapper<>();

        if(StringUtils.isNoneBlank(query)){
            queryWrapper.like("account" , query);
            queryWrapper.like("number", query);
        }



        queryWrapper.orderByDesc("gmt_create");
        this.orderService.page(page, queryWrapper);

        return R.ok().data("total", page.getTotal()).data("rows", page.getRecords());
    }
}

