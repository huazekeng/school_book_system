package com.mrli.school_book.schedule;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mrli.school_book.entity.TBorrowrecord;
import com.mrli.school_book.entity.TNotice;
import com.mrli.school_book.entity.TReader;
import com.mrli.school_book.service.TBorrowrecordService;
import com.mrli.school_book.service.TNoticeService;
import com.mrli.school_book.service.TReaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 书籍定时器，用来看那个扑街没有按期归还书籍
 */
@Component
@Slf4j
public class BookSchedule {

    @Autowired
    private TBorrowrecordService borrowrecordService;

    @Autowired
    private TReaderService readerService;

    @Autowired
    private TNoticeService noticeService;

    //每隔1分钟执行一次
    @Scheduled(fixedRate = 1000 * 60)
    @Transactional
    public void testTasks() {

        QueryWrapper<TBorrowrecord> queryWrapper = new QueryWrapper<>();
        // 状态为未换，且归还时间大于当前
        queryWrapper.eq("status", 0);
        queryWrapper.le("inttime", new Date());

        List<TBorrowrecord> list = this.borrowrecordService.list(queryWrapper);
        list.forEach(item -> {
            item.setStatus(2);
            TReader reader = readerService.getById(item.getRid());
            // 扣信用分
            reader.setCredit(reader.getCredit() - 5 < 0 ? 0 : reader.getCredit());
            if(reader.getCredit() <= 0) {
                reader.setCondi(1);
            }
            readerService.updateById(reader);
            TNotice notice = new TNotice();
            notice.setTitle("通告批评");
            log.info(reader.getName() + "同学未按时归还书籍，给予通告批评处分，并扣除信用分5分，以儆效尤");
            notice.setContent(reader.getName() + "同学未按时归还书籍，给予通告批评处分，并扣除信用分5分，以儆效尤");
            noticeService.save(notice);
        });
        if(list!=null && list.size()>0) {
            borrowrecordService.updateBatchById(list);
        }
    }


}
