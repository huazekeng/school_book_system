package com.mrli.school_book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.school_book.entity.TAlbum;
import com.mrli.school_book.entity.TBorrowrecord;
import com.mrli.school_book.entity.TReader;
import com.mrli.school_book.form.SearchForm;
import com.mrli.school_book.mapper.TAlbumMapper;
import com.mrli.school_book.mapper.TBorrowrecordMapper;
import com.mrli.school_book.mapper.TReaderMapper;
import com.mrli.school_book.service.TReaderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrli.school_book.vo.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Harry
 * @since 2020-03-26
 */
@Service
public class TReaderServiceImpl extends ServiceImpl<TReaderMapper, TReader> implements TReaderService {

    @Autowired
    private TBorrowrecordMapper recordMapper;

    @Autowired
    private TAlbumMapper albumMapper;


    @Override
    public TReader getByAccount(String username) {
        QueryWrapper<TReader> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", username);
        return this.getOne(queryWrapper);
    }

    @Transactional
    public void reabck(String account, Integer bid) {
        TReader reader = getByAccount(account);
        TBorrowrecord record = recordMapper.selectById(bid);
        record.setBacktime(new Date()); // 设置借阅为归还
        if(record.getStatus() == 2  || (record.getInttime().getTime() - new Date().getTime() < 0)) {
            record.setStatus(3);  // 逾期归还
        } else {
            record.setStatus(1);  // 按期归还
        }


        TAlbum book = albumMapper.selectById(record.getAid());
        book.setRemain(book.getRemain() + 1);// 书籍剩余 + 1
        // book.setTotal(book.getTotal() + 1); // 借阅次数 + 1
        reader.setCredit(reader.getCredit() + 1); // 信用+1

        this.updateById(reader);
        recordMapper.updateById(record);
        albumMapper.updateById(book);
    }

    @Transactional
    public R borrow(TReader user, Integer id) {
        TAlbum book = this.albumMapper.selectById(id);
        // 租书人
        TReader borrower = getByAccount(book.getUsername());

        // 不够积分，直接返回
        Integer integation = user.getIntegation();
        if(integation.intValue() < book.getIntegration()) {
            return R.error().message("积分不够");
        }

        // 借书人扣分
        user.setIntegation(user.getIntegation() - book.getIntegration());
        // 加借阅记录
        TBorrowrecord record = new TBorrowrecord();
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DATE, book.getPeroid());
        record.setRaccount(user.getAccount());
        record.setStatus(0);
        record.setAid(book.getAid());
        record.setGmtCreate(now);
        record.setInttime(calendar.getTime());
        record.setRid(user.getRid());
        // 书籍可借少1
        book.setRemain(book.getRemain() - 1);
        // 书籍借阅次数加1
        book.setTotal(book.getTotal() + 1);
        // 租书人加积分
        borrower.setIntegation(borrower.getIntegation() + book.getIntegration());
        updateById(user);
        updateById(borrower);
        recordMapper.insert(record);
        albumMapper.updateById(book);
        return R.ok();
    }


}
