package com.mrli.school_book.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.school_book.entity.TAlbum;
import com.mrli.school_book.entity.TBooktype;
import com.mrli.school_book.security.JwtConfig;
import com.mrli.school_book.service.TAlbumService;
import com.mrli.school_book.service.TBooktypeService;
import com.mrli.school_book.util.RedisUtils;
import com.mrli.school_book.vo.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  分类控制器
 * </p>
 *
 * @author Harry
 * @since 2020-03-29
 */
@RestController
@RequestMapping("/admin/booktype")
public class TBooktypeController {


    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private TBooktypeService booktypeService;

    /**
     * 分类列比奥
     * @param currPage 当前页
     * @param limit 每页条目数
     * @param query 查询参数
     * @return
     */
    @GetMapping("/{currPage}/{limit}")
    public R list(@PathVariable("currPage")Integer currPage, @PathVariable("limit")Integer limit , @RequestParam(value = "query",required = false)String query) {

        Page<TBooktype> page = new Page<>();
        if(currPage.intValue() == -1 || limit.intValue() == -1){
            return R.ok().data("rows",  this.booktypeService.list(null));
        }

        page.setCurrent(currPage);
        page.setSize(limit);
        QueryWrapper<TBooktype> queryWrapper = new QueryWrapper<>();


        if(StringUtils.isNoneBlank(query)){
            queryWrapper.like("name",query);
        }

        this.booktypeService.page(page, queryWrapper);

        return R.ok().data("total", page.getTotal()).data("rows", page.getRecords());
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id")Integer id) {
        this.booktypeService.removeById(id);
        return R.ok();
    }

    @PostMapping()
    public R add(@RequestBody TBooktype booktype) {
        this.booktypeService.save(booktype);
        return R.ok();
    }
}

