package com.dove.book.provider.book.dto;

import com.dove.common.base.validate.QueryGroup;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class BookDto implements Serializable {

    private Integer pageNum = 1;
    private Integer pageSize = 10;
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @NotBlank(groups={Select.class},message = "【id】不能为空")

    @NotBlank(groups = {QueryGroup.Update.class}, message = "【id:书籍id】不能为空")
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "书名")
    @NotEmpty(groups = {QueryGroup.Insert.class}, message = "【name:书名】不能为空")
    private String name;

    @ApiModelProperty(value = "书的简介")
    private String desc;

    @ApiModelProperty(value = "书主对书的介绍")
    private String userDesc;

    @ApiModelProperty(value = "押金")
    @NotBlank(groups = {QueryGroup.Insert.class}, message = "【price:押金】不能为空")
    private Long price;

    @ApiModelProperty(value = "租赁价格")
    @NotBlank(groups = {QueryGroup.Insert.class}, message = "【rentPrice:租金】不能为空")
    private Long rentPrice;

    @ApiModelProperty(value = "状态：0-空闲；1-已租；9-下架")
    private Integer status;

    @ApiModelProperty(value = "热度")
    private Long star;

    @ApiModelProperty(value = "种类Id")
    private Long categoryId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(Long rentPrice) {
        this.rentPrice = rentPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getStar() {
        return star;
    }

    public void setStar(Long star) {
        this.star = star;
    }

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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}