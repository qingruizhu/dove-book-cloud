package com.dove.book.sso.provider.controller;

import com.dove.book.bgd.model.Permission;
import com.dove.book.bgd.model.User;
import com.dove.book.bgd.service.IUserService;
import com.dove.book.sso.api.dto.UserDto;
import com.dove.book.sso.provider.exception.SsoException;
import com.dove.book.sso.provider.service.ICommonService;
import com.dove.book.sso.provider.vo.PermissionNode;
import com.dove.book.sso.provider.vo.UserVo;
import com.dove.common.base.annotation.CommonResultAnnon;
import com.dove.common.base.validate.QueryGroup;
import com.dove.common.shiro.core.holder.UserHolder;
import com.dove.common.shiro.server.util.EncrypPwdUtil;
import com.dove.common.util.data.TreeNodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.dove.book.sso.provider.enm.SsoErrorEnum.*;


/**
 * @Description: 我的信息
 * @Auther: qingruizhu
 * @Date: 2019-12-05 16:19
 */
@Api(description = "操作【个人信息】", tags = "☻ ME")
@CommonResultAnnon
@RestController
@RequestMapping("/me")
public class MeController {

    @Autowired
    IUserService<UserDto> service;
    @Autowired
    ICommonService commonService;

    @ApiOperation("查询【我的信息】")
    @GetMapping(value = "/info")
    public UserVo info() {
        User select = service.select(UserHolder.getUid());
        select.setPassword(null);
        select.setPwdSalt(null);
        UserVo vo = new UserVo();
        BeanUtils.copyProperties(select, vo);
        List<Permission> permissions = service.listPermissions(select.getId());
        List<PermissionNode> nodes = permissions.stream().map(permission -> {
            PermissionNode node = new PermissionNode();
            BeanUtils.copyProperties(permission, node);
            return node;
        }).collect(Collectors.toList());
        List<? extends TreeNodeUtil.Node> trees = TreeNodeUtil.convertTree(nodes);
        vo.setPermissions(trees);
        return vo;
    }

    @ApiOperation("修改/设置【手机号】")
    @PostMapping("/updatePhone")
    public void updatePhone(
            @ApiParam("手机号") @RequestParam String phone,
            @ApiParam("验证码") @RequestParam String authCode) {
        // 1.校验验证码
        commonService.checkAuthCode(phone, authCode);
        User user = new User();
        user.setPhone(phone);
        user.setId(UserHolder.getUid());
        //2.修改
        if (service.update(user) <= 0)
            throw new SsoException(SSO_ME_ERROR_PHONE_UPDATE);
    }

    @ApiOperation("修改【密码】")
    @PostMapping("/updatePassword")
    public void updatePassword(
            @ApiParam("原始密码") @RequestParam String originalPwd,
            @ApiParam("新密码 ") @RequestParam String targetPwd,
            @ApiParam("验证码") @RequestParam String authCode) {

        User user = service.select(UserHolder.getUid());
        String oldPwd;
        oldPwd = EncrypPwdUtil.sha256(originalPwd, UserHolder.getUser().getPwdSalt());
        //1.校验老密码
        if (!user.getPassword().equals(oldPwd)) {
            throw new SsoException(SSO_ME_ERROR_PWD_UPDATE_OLD);
        }
        if (StringUtils.isBlank(user.getPhone())) {
            throw new SsoException(SSO_ME_ERROR_PWD_UPDATE_HVT_PHONE);
        }
        // 2.校验验证码
        commonService.checkAuthCode(user.getPhone(), authCode);
        // 3.修改密码
        User newUser = new User();
        newUser.setId(user.getId());
        String newSalt = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
        newUser.setPassword(EncrypPwdUtil.sha256(targetPwd, newSalt));
        newUser.setPwdSalt(newSalt);
        if (service.update(newUser) <= 0) {
            throw new SsoException(SSO_ME_ERROR_PWD_UPDATE);
        }
    }

    @ApiOperation("初始化【密码】")
    @PostMapping("/initPassword")
    public void initPassword(
            @ApiParam("密码") @RequestParam String password,
            @ApiParam("验证码") @RequestParam String authCode) {
        //1.用户是否存在密码
        User query = service.select(UserHolder.getUid());
        if (StringUtils.isNotBlank(query.getPassword())) {
            throw new SsoException(SSO_ME_ERROR_PWD_INIT_EXIST);
        }
        // 2.校验验证码
        commonService.checkAuthCode(query.getPhone(), authCode);
        User user = new User();
        user.setId(query.getId());
        String salt = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
        user.setPassword(EncrypPwdUtil.sha256(password, salt));
        user.setPwdSalt(salt);
        if (service.update(user) <= 0) {
            throw new SsoException(SSO_ME_ERROR_PWD_INIT);
        }

    }

    @ApiOperation("修改【我的信息】")
    @PostMapping("/updateInfo")
    public void updateInfo(
            @RequestBody @Validated(QueryGroup.Update.class) UserDto user) {
        user.setId(UserHolder.getUid());
        if (service.updateQ(user) <= 0)
            throw new SsoException(SSO_ME_ERROR_INFO_UPDATE);
    }


}
