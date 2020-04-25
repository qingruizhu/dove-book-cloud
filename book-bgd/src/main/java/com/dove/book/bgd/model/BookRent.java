package com.dove.book.bgd.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class BookRent implements Serializable {
    private Long id;

    @ApiModelProperty(value = "书id")
    private Long bookId;

    @ApiModelProperty(value = "租赁者id")
    private Long rentUserId;

    @ApiModelProperty(value = "实际租赁价格")
    private Long rentPrice;

    @ApiModelProperty(value = "租赁者地址")
    private String rentUserAddress;

    @ApiModelProperty(value = "当前状态：0-申请；1-待发货；2-待收货；3-待归还；4-结束")
    private Integer status;

    @ApiModelProperty(value = "租书评价")
    private String rentDesc;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getRentUserId() {
        return rentUserId;
    }

    public void setRentUserId(Long rentUserId) {
        this.rentUserId = rentUserId;
    }

    public Long getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(Long rentPrice) {
        this.rentPrice = rentPrice;
    }

    public String getRentUserAddress() {
        return rentUserAddress;
    }

    public void setRentUserAddress(String rentUserAddress) {
        this.rentUserAddress = rentUserAddress;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRentDesc() {
        return rentDesc;
    }

    public void setRentDesc(String rentDesc) {
        this.rentDesc = rentDesc;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", bookId=").append(bookId);
        sb.append(", rentUserId=").append(rentUserId);
        sb.append(", rentPrice=").append(rentPrice);
        sb.append(", rentUserAddress=").append(rentUserAddress);
        sb.append(", status=").append(status);
        sb.append(", rentDesc=").append(rentDesc);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}