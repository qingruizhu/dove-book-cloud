package com.dove.sso.controller;

import com.dove.book.bgd.model.Permission;
import com.dove.book.bgd.model.User;
import com.dove.book.bgd.service.IUserService;
import com.dove.common.base.annotation.CommonResultAnnon;
import com.dove.common.shiro.credential.ShiroUser;
import com.dove.common.util.data.TreeNodeUtil;
import com.dove.sso.dto.UserDto;
import com.dove.sso.vo.PermissionNode;
import com.dove.sso.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CommonResultAnnon
@RestController
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    IUserService<UserDto> service;

    @GetMapping(value = "/info")
    public UserVo info() {
        Subject subject = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) subject.getPrincipal();
        User select = service.select(user.getId());
        select.setPassword(null);
        UserVo vo = new UserVo();
        BeanUtils.copyProperties(select, vo);
        List<Permission> permissions = service.listPermissions(user.getId());
        List<PermissionNode> nodes = permissions.stream().map(permission -> {
            PermissionNode node = new PermissionNode();
            BeanUtils.copyProperties(permission, node);
            return node;
        }).collect(Collectors.toList());
        List<? extends TreeNodeUtil.Node> trees = TreeNodeUtil.convertTree(nodes);
        vo.setPermissions(trees);
        return vo;
    }

}
