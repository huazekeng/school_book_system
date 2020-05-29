package com.mrli.school_book.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.school_book.entity.TUser;
import com.mrli.school_book.security.JwtConfig;
import com.mrli.school_book.security.JwtUser;
import com.mrli.school_book.service.TUserService;
import com.mrli.school_book.util.RedisUtils;
import com.mrli.school_book.vo.R;
import net.bytebuddy.asm.Advice;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  管理员控制器
 * </p>
 *
 * @author Harry
 * @since 2020-03-26
 */
@RestController
@RequestMapping("/admin/user")
public class TUserController {

    @Autowired
    private TUserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("/{currPage}/{limit}")
    public R list(@PathVariable("currPage")Integer currPage, @PathVariable("limit")Integer limit , @RequestParam(value = "query",required = false)String query) {

        Page<TUser> page = new Page<>();
        page.setCurrent(currPage);
        page.setSize(limit);

        QueryWrapper<TUser> queryWrapper = new QueryWrapper<>();

        if(StringUtils.isNoneBlank(query)){
            queryWrapper.like("username",query);
            queryWrapper.or().like("email", query);

        }

        this.userService.page(page, queryWrapper);

        return R.ok().data("total", page.getTotal()).data("rows", page.getRecords());
    }


    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id")Integer id) {


        this.userService.removeById(id);

        return R.ok();
    }

    @PostMapping()
    public R add(@RequestBody TUser tUser) {
        if(this.userService.getByName(tUser.getUsername())!=null) {
            return R.error().message("用户名已经存在");
        }
        if(this.userService.getByEmail(tUser.getEmail())!=null) {
            return R.error().message("改邮箱已经注册");
        }
        tUser.setPassword(passwordEncoder.encode("123456"));
        this.userService.save(tUser);
        return R.ok();
    }

    @PutMapping("/resetPwd/{id}")
    public R resetPwd(@PathVariable("id") Integer id ){
        TUser byId = this.userService.getById(id);
        byId.setPassword(passwordEncoder.encode("123456"));
        this.userService.updateById(byId);
        return R.ok();
    }

    @PutMapping("/modifyPwd/{id}")
    public R modifyPwd(@PathVariable("id") Integer id , @RequestParam("pwd")String pwd){
        TUser byId = this.userService.getById(id);
        byId.setPassword(passwordEncoder.encode(pwd));
        this.userService.updateById(byId);
        return R.ok();
    }

    @GetMapping("/loginUser")
    public R loginUser(HttpServletRequest request){
        String authHeader = request.getHeader(jwtConfig.getHeader());
        String authToken = authHeader.substring(jwtConfig.getTokenHead().length() + 1);
        JwtUser user = (JwtUser) redisUtils.get(authToken);
        return R.ok().data("user", user);
    }
}

