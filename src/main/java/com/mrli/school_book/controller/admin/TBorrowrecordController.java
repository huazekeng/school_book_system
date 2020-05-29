package com.mrli.school_book.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.school_book.dto.admin.RecordToVo;
import com.mrli.school_book.entity.TBorrowrecord;
import com.mrli.school_book.entity.TUser;
import com.mrli.school_book.security.JwtConfig;
import com.mrli.school_book.service.TBorrowrecordService;
import com.mrli.school_book.util.RedisUtils;
import com.mrli.school_book.vo.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  借阅记录控制器
 * </p>
 *
 * @author Harry
 * @since 2020-03-26
 */
@RestController
@RequestMapping("/admin/borrowrecord")
public class TBorrowrecordController {

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private TBorrowrecordService borrowrecordService;

    @Autowired
    private RecordToVo recordToVo;

    @GetMapping("/{currPage}/{limit}")
    public R list(@PathVariable("currPage")Integer currPage, @PathVariable("limit")Integer limit , @RequestParam(value = "query",required = false)String query) {

        Page<TBorrowrecord> page = new Page<>();
        page.setCurrent(currPage);
        page.setSize(limit);

        QueryWrapper<TBorrowrecord> queryWrapper = new QueryWrapper<>();

        if(StringUtils.isNoneBlank(query)){
            queryWrapper.like("raccount",query);
        }

        this.borrowrecordService.page(page, queryWrapper);

        return R.ok().data("total", page.getTotal()).data("rows", recordToVo.toVo(page.getRecords()));
    }
}

