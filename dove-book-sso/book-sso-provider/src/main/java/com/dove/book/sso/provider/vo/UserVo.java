package com.dove.book.sso.provider.vo;

import com.dove.common.util.data.TreeNodeUtil;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class UserVo implements Serializable {
    private List<? extends TreeNodeUtil.Node> permissions;

    private Long id;

    @ApiModelProperty(value = "账号")
    private String username;
//
//    @ApiModelProperty(value = "密码")
//    private String password;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "姓名")
    private String realname;

    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "头像 ")
    private String icon;

    @ApiModelProperty(value = "信用值")
    private Long growth;

    @ApiModelProperty(value = "热度")
    private Long star;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getGrowth() {
        return growth;
    }

    public void setGrowth(Long growth) {
        this.growth = growth;
    }

    public Long getStar() {
        return star;
    }

    public void setStar(Long star) {
        this.star = star;
    }

    public List<? extends TreeNodeUtil.Node> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<? extends TreeNodeUtil.Node> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
//        sb.append(", password=").append(password);
        sb.append(", phone=").append(phone);
        sb.append(", realname=").append(realname);
        sb.append(", sex=").append(sex);
        sb.append(", icon=").append(icon);
        sb.append(", growth=").append(growth);
        sb.append(", star=").append(star);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}