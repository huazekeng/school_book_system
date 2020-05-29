package com.mrli.school_book.form;

import lombok.Data;

@Data
public class SearchForm {

    private Integer currPage;

    private Integer pageSize;

    private Integer descType;

    private String descCol;

    private String keyword;
}
