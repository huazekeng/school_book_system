package com.mrli.school_book.controller.api;

import com.mrli.school_book.entity.TAlbum;
import com.mrli.school_book.entity.TBorrowrecord;
import com.mrli.school_book.entity.TOrder;
import com.mrli.school_book.entity.TReader;
import com.mrli.school_book.security.JwtConfig;
import com.mrli.school_book.security.JwtUser;
import com.mrli.school_book.security.JwtUtils;
import com.mrli.school_book.service.TAlbumService;
import com.mrli.school_book.service.TBorrowrecordService;
import com.mrli.school_book.service.TOrderService;
import com.mrli.school_book.service.TReaderService;
import com.mrli.school_book.util.RedisUtils;
import com.mrli.school_book.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 学生管理控制器
 */
@RestController
@RequestMapping("/api/reader")
public class ReaderController {

    @Autowired
    private TReaderService readerService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private TBorrowrecordService recordService;

    @Autowired
    private TAlbumService bookService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    @Autowired
    private TOrderService orderService;
    /**
     * 登录
     * @param param
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestBody Map<String,String> param) {

        String username = param.get("account");
        String password = param.get("password");
        TReader reader = readerService.getByAccount(username);
        if(reader == null || !passwordEncoder.matches(password, reader.getPassword())) {
            return R.error().message("用户名或密码错误");
        } else if(reader.getCondi().intValue()== 1) {
            return R.error().message("改账户已经被注销，请联系管理员");
        }
        //生成jwt
        String token =  JwtUtils.createJwtToken(username,jwtConfig.getExpiresSecond());
        //生成refresh_token
        String refreshToken = JwtUtils.createJwtToken(UUID.randomUUID().toString(),jwtConfig.getRefreshExpiresSecond());
        R result = R.ok().data("token",jwtConfig.getTokenHead() + " " + token).data("refresh_token",refreshToken).data("user", reader);
        // 將token和userDetail緩存至Redis , 最長存放一天
        redisUtils.set(token, reader, 1l , TimeUnit.DAYS);
        redisUtils.set(refreshToken,"api" + username,1l , TimeUnit.DAYS);
        return result;

    }

    /**
     * 获取当前登录用户
     * @param request
     * @return
     */
    @GetMapping("/loginUser")
    public R getLoginUser(HttpServletRequest request) {
        // 获取reids缓存的用户信息
        String authHeader = request.getHeader(jwtConfig.getHeader());
        String authToken = authHeader.substring(jwtConfig.getTokenHead().length() + 1);
        TReader user = (TReader) redisUtils.get(authToken);
        TReader reader = readerService.getByAccount(user.getAccount());
        return R.ok().data("data", reader);
    }

    /**
     * 注册
     * @param param
     * @return
     */
    @PostMapping("/register")
    public R register(@RequestBody Map<String,String> param) {
        String account = param.get("account");
        String password = param.get("password");
        String name = param.get("name");
        TReader reader = readerService.getByAccount(account);
        if(reader != null) {
            return R.error().message("该学号已经注册，请直接登录");
        }
        reader = new TReader();
        reader.setAccount(account);
        reader.setName(name);
        reader.setPassword(passwordEncoder.encode(password));
        this.readerService.save(reader);
        return R.ok();
    }

    /**
     * 个人信息编辑
     * @param request
     * @param reader
     * @return
     */
    @PutMapping()
    @Transactional
    public R edit(HttpServletRequest request, @RequestBody TReader reader) {
        String authHeader = request.getHeader(jwtConfig.getHeader());
        String authToken = authHeader.substring(jwtConfig.getTokenHead().length() + 1);
        TReader user = (TReader) redisUtils.get(authToken);
        reader.setRid(user.getRid());
        this.readerService.updateById(reader);
        redisUtils.set(authToken, this.readerService.getById(user.getRid()), 1l , TimeUnit.DAYS);
        return R.ok().data("data", reader);
    }

    /**
     * 归还书籍
     * @param request
     * @param bid
     * @return
     */
    @PutMapping("/reback/{bid}")
    public R reback(HttpServletRequest request, @PathVariable("bid")Integer bid) {
        String authHeader = request.getHeader(jwtConfig.getHeader());
        String authToken = authHeader.substring(jwtConfig.getTokenHead().length() + 1);
        TReader user = (TReader) redisUtils.get(authToken);
        String account = user.getAccount();
        this.readerService.reabck(account, bid);
        return R.ok();
    }

    /**
     * 催还书籍
     * @param bid
     * @return
     */
    @PutMapping("/callreback/{bid}")
    public R callreback(@PathVariable("bid")Integer bid) {
        TBorrowrecord record = recordService.getById(bid);
        Integer rid = record.getRid();
        TReader reader = readerService.getById(rid);
        TAlbum bookd = bookService.getById(record.getAid());
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromMail);//发送者
        msg.setTo(reader.getEmail());//接收者
        msg.setSubject("校园内书城书籍催还");//标题
        msg.setText("你借的书名为<" + bookd.getTitle() + ">的书籍已经逾期归还，请尽快归还书籍");//内容
        javaMailSender.send(msg);
        return R.ok();
    }


    /**
     * 积分充值
     * @param order
     * @return
     */
    @PutMapping("/pay")
    public R pay(@RequestBody TOrder order) {
        order.setNumber(new Date().getTime() + "");
        orderService.save(order);
        TReader byAccount = readerService.getByAccount(order.getAccount());
        byAccount.setIntegation(byAccount.getIntegation() + order.getPrice().multiply(new BigDecimal(10)).intValue());
        readerService.updateById(byAccount);
        return R.ok();
    }

    /**
     * 借阅
     * @param id
     * @return
     */
    @PostMapping("/borrow/{id}")
    public R borrow(HttpServletRequest request,@PathVariable("id")Integer id) {
        String authHeader = request.getHeader(jwtConfig.getHeader());
        String authToken = authHeader.substring(jwtConfig.getTokenHead().length() + 1);
        TReader user = (TReader) redisUtils.get(authToken);
        return readerService.borrow(user, id);
    }
}
