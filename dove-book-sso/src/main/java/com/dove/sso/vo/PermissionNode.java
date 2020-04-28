package com.dove.sso.vo;

import com.dove.book.bgd.model.Permission;
import com.dove.common.util.data.TreeNodeUtil;

import java.util.List;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/4/28 18:07
 */
public class PermissionNode extends Permission implements TreeNodeUtil.Node<PermissionNode> {
    private List<PermissionNode> children;

    @Override
    public Long getParentId() {
        return super.getPid();
    }

    @Override
    public List<PermissionNode> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<PermissionNode> children) {
        this.children = children;
    }
}
