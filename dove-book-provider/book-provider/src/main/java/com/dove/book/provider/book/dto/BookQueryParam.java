package com.dove.book.provider.book.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class BookQueryParam implements Serializable {

    private Integer pageNum = 1;
    private Integer pageSize = 5;

    @ApiModelProperty(value = "书名")
    private String name;

    @ApiModelProperty(value = "种类Id")
    private Long categoryId;

    private static final long serialVersionUID = 1L;


    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}