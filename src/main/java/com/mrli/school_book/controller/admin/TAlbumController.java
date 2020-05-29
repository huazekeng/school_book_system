package com.mrli.school_book.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.school_book.entity.TAlbum;
import com.mrli.school_book.entity.TUser;
import com.mrli.school_book.security.JwtConfig;
import com.mrli.school_book.security.JwtUser;
import com.mrli.school_book.service.TAlbumService;
import com.mrli.school_book.service.TBooktypeService;
import com.mrli.school_book.util.RedisUtils;
import com.mrli.school_book.vo.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  书籍控制器
 * </p>
 *
 * @author Harry
 * @since 2020-03-26
 */
@RestController
@RequestMapping("/admin/talbum")
public class TAlbumController {


    @Autowired
    private TAlbumService albumService;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private TBooktypeService booktypeService;

    /**
     * 书籍列表
     * @param currPage
     * @param limit
     * @param query
     * @return
     */
    @GetMapping("/{currPage}/{limit}")
    public R list(@PathVariable("currPage")Integer currPage, @PathVariable("limit")Integer limit , @RequestParam(value = "query",required = false)String query) {

        Page<TAlbum> page = new Page<>();

        page.setCurrent(currPage);
        page.setSize(limit);
        QueryWrapper<TAlbum> queryWrapper = new QueryWrapper<>();

        if(StringUtils.isNoneBlank(query)){
            queryWrapper.like("author",query);
            queryWrapper.or().like("title", query);

        }

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
        JwtUser user = (JwtUser) redisUtils.get(authToken);
        tAlbum.setUsername(user.getUsername());
        tAlbum.setRemain(tAlbum.getNum());
        tAlbum.setCname(booktypeService.getById(tAlbum.getCid()).getName());
        this.albumService.save(tAlbum);
        return R.ok();
    }
}

