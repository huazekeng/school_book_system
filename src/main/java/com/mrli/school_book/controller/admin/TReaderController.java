package com.mrli.school_book.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.school_book.entity.TReader;
import com.mrli.school_book.entity.TUser;
import com.mrli.school_book.security.JwtConfig;
import com.mrli.school_book.service.TReaderService;
import com.mrli.school_book.service.TUserService;
import com.mrli.school_book.util.RedisUtils;
import com.mrli.school_book.vo.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  学生控制器
 * </p>
 *
 * @author Harry
 * @since 2020-03-26
 */
@RestController
@RequestMapping("/admin/reader")
public class TReaderController {

    @Autowired
    private TReaderService readerService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 所有学生
     * @param currPage
     * @param limit
     * @param query
     * @return
     */
    @GetMapping("/{currPage}/{limit}")
    public R list(@PathVariable("currPage")Integer currPage, @PathVariable("limit")Integer limit , @RequestParam(value = "query",required = false)String query) {

        Page<TReader> page = new Page<>();
        page.setCurrent(currPage);
        page.setSize(limit);

        QueryWrapper<TReader> queryWrapper = new QueryWrapper<>();

        if(StringUtils.isNoneBlank(query)){
            queryWrapper.like("account",query);
            queryWrapper.or().like("email", query);
            queryWrapper.or().like("name", query);
        }

        this.readerService.page(page, queryWrapper);

        return R.ok().data("total", page.getTotal()).data("rows", page.getRecords());
    }


    /**
     * 注销账户
     * @param id
     * @return
     */
    @PutMapping("/disactive/{id}")
    public R up(@PathVariable("id") Integer id ){
        TReader byId = this.readerService.getById(id);
        byId.setCondi(1);
        this.readerService.updateById(byId);
        return R.ok();
    }

    /**
     * 激活账户
     * @param id
     * @return
     */
    @PutMapping("/active/{id}")
    public R down(@PathVariable("id") Integer id ){
        TReader byId = this.readerService.getById(id);
        byId.setCondi(0);
        this.readerService.updateById(byId);
        return R.ok();
    }

}

