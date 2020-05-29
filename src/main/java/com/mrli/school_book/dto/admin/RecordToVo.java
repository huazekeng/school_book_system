package com.mrli.school_book.dto.admin;

import com.mrli.school_book.entity.TAlbum;
import com.mrli.school_book.entity.TBorrowrecord;
import com.mrli.school_book.entity.TReader;
import com.mrli.school_book.service.TAlbumService;
import com.mrli.school_book.service.TReaderService;
import com.mrli.school_book.vo.admin.RecordVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RecordToVo {


    @Autowired
    private TAlbumService albumService;

    @Autowired
    private TReaderService readerService;

    public  List<RecordVo> toVo(List<TBorrowrecord> rs){
        List<RecordVo> list = new ArrayList<>();
        rs.forEach(item -> {
            RecordVo vo = new RecordVo();
            BeanUtils.copyProperties(item, vo);
            vo.setTitle(albumService.getById(vo.getAid()).getTitle());
            vo.setRname(readerService.getById(item.getRid()).getName());
            list.add(vo);
        });
        return list;
    }

}
