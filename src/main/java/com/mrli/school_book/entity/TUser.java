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
@ApiModel(value="TUser对象", description="")
public class TUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "userid", type = IdType.AUTO)
    private Integer userid;

    private String email;

    private String username;

    private String password;

    @ApiModelProperty(value = "0：已注册，未激活；1：已激活，普通用户；2：异常用户，限制使用")
    private Integer condi;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

}
