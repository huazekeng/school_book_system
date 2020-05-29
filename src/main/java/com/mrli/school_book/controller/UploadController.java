package com.mrli.school_book.controller;

import com.mrli.school_book.vo.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * 上传图片控制器
 */
@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @RequestMapping()
    public R upload(MultipartFile file, Integer type) {
        String path = "";
        String fileName = "";
        String temp = "tmp";
        try {
            temp = "images/book";
            String parentPath = UploadController.class.getResource("/static/" + temp + "/").getPath();
            fileName = UUID.randomUUID().toString() + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
            path = parentPath + fileName;
            File tmpFile = new File(path);
            file.transferTo(tmpFile);
        }catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
        return R.ok().data("path", "/static/" + temp + "/" + fileName).data("url","http://localhost/" + temp + "/" + fileName).data("type",type);
    }
}
