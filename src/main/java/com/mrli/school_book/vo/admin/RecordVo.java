package com.mrli.school_book.vo.admin;

import com.mrli.school_book.entity.TBorrowrecord;
import lombok.Data;

@Data
public class RecordVo extends TBorrowrecord {

    private String title;

    private String rname;

}
