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
@ApiModel(value="TBorrowrecord对象", description="")
public class TBorrowrecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "bid", type = IdType.AUTO)
    private Integer bid;

    private Integer rid;

    @ApiModelProperty(value = "读者账号")
    private String raccount;

    private Integer aid;

    private Integer sid;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date backtime;

    @ApiModelProperty(value = "判断是否超期")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date inttime;

    private Integer status;
}
