package com.mrli.school_book.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.school_book.entity.TAlbum;
import com.mrli.school_book.entity.TReader;
import com.mrli.school_book.form.SearchForm;
import com.mrli.school_book.security.JwtConfig;
import com.mrli.school_book.security.JwtUser;
import com.mrli.school_book.service.TAlbumService;
import com.mrli.school_book.service.TBooktypeService;
import com.mrli.school_book.service.TReaderService;
import com.mrli.school_book.util.RedisUtils;
import com.mrli.school_book.vo.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 书籍管理控制器
 */
@RestController
@RequestMapping("/api/book")
public class BookController {


    @Autowired
    private TAlbumService albumService;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private TBooktypeService booktypeService;

    @Autowired
    private TReaderService readerService;

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

        Page<TAlbum> page = new Page<>();

        page.setCurrent(currPage);
        page.setSize(limit);
        QueryWrapper<TAlbum> queryWrapper = new QueryWrapper<>();

        if(type != null && type == 2) {
            String authHeader = request.getHeader(jwtConfig.getHeader());
            String authToken = authHeader.substring(jwtConfig.getTokenHead().length() + 1);
            TReader user = (TReader) redisUtils.get(authToken);
            queryWrapper.eq("username", user.getAccount());
        }
        if(StringUtils.isNoneBlank(query)){
            queryWrapper.and(wrapper -> wrapper.like("title", query).or().like("author", query));
        }


        queryWrapper.orderByDesc("gmt_create");
        this.albumService.page(page, queryWrapper);

        return R.ok().data("total", page.getTotal()).data("rows", page.getRecords());
    }

    /**
     * 上架
     * @param id
     * @return
     */
    @PutMapping("/up/{id}")
    public R up(@PathVariable("id") Integer id ){
        TAlbum byId = this.albumService.getById(id);
        byId.setStatus(1);
        this.albumService.updateById(byId);
        return R.ok();
    }

    /**
     * 下架
     * @param id
     * @return
     */
    @PutMapping("/down/{id}")
    public R down(@PathVariable("id") Integer id ){
        TAlbum byId = this.albumService.getById(id);
        byId.setStatus(0);
        this.albumService.updateById(byId);
        return R.ok();
    }

    /**
     * 新增
     * @param tAlbum
     * @param request
     * @return
     */
    @PostMapping()
    public R add(@RequestBody TAlbum tAlbum , HttpServletRequest request){
        String authHeader = request.getHeader(jwtConfig.getHeader());
        String authToken = authHeader.substring(jwtConfig.getTokenHead().length() + 1);
        TReader user = (TReader) redisUtils.get(authToken);
        tAlbum.setUsername(user.getAccount());
        tAlbum.setRemain(tAlbum.getNum());
        tAlbum.setCname(booktypeService.getById(tAlbum.getCid()).getName());
        this.albumService.save(tAlbum);
        return R.ok();
    }


    @GetMapping("/detail/{id}")
    public R detail(@PathVariable Integer id) {
        TAlbum book = this.albumService.getById(id);
        TReader reader = this.readerService.getByAccount(book.getUsername());
        return R.ok().data("user", reader).data("book", book);
    }

    /**
     * 书籍搜索
     * @param form
     * @return
     */
    @GetMapping("/search")
    public R search(@ModelAttribute SearchForm form) {
        Page page = this.albumService.search(form);
        return R.ok().data("rows", page.getRecords()).data("total", page.getTotal());
    }

}
