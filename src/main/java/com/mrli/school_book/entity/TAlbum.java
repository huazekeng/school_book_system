package com.mrli.school_book.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Harry
 * @since 2020-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="TAlbum对象", description="")
public class TAlbum implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "aid", type = IdType.AUTO)
    private Integer aid;

    private String title;

    private String author;

    private String publisher;

    private String publishtime;

    private Integer num;

    private String descri;

    private Integer integration;

    private Integer peroid;

    private Integer remain;

    private Integer status;

    private String username;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    private Integer cid;

    private String cname;

    private String bookpic;

    private Integer total;
}
