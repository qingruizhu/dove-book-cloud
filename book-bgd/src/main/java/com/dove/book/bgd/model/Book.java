package com.dove.book.bgd.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class Book implements Serializable {
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "书名")
    private String name;

    @ApiModelProperty(value = "书的简介")
    private String bookDesc;

    @ApiModelProperty(value = "书主对书的介绍")
    private String userDesc;

    @ApiModelProperty(value = "押金")
    private Long price;

    @ApiModelProperty(value = "租赁价格")
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

    public String getBookDesc() {
        return bookDesc;
    }

    public void setBookDesc(String bookDesc) {
        this.bookDesc = bookDesc;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", name=").append(name);
        sb.append(", bookDesc=").append(bookDesc);
        sb.append(", userDesc=").append(userDesc);
        sb.append(", price=").append(price);
        sb.append(", rentPrice=").append(rentPrice);
        sb.append(", status=").append(status);
        sb.append(", star=").append(star);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}