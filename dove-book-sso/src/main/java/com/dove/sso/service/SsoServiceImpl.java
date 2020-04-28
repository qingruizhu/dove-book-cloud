package com.dove.sso.service;

import com.dove.common.jwt.util.JwtTokenUtil;
import com.dove.common.shiro.credential.ShiroUser;
import com.dove.common.shiro.token.PhoneToken;
import com.dove.sso.exception.SsoException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.dove.sso.enm.SsoErrorEnum.SSO_ERROR_AUTHCODE;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/4/27 11:57
 */
@Service
public class SsoServiceImpl implements SsoService {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    public String login(String name, String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        subject.login(token);
        ShiroUser user = (ShiroUser) subject.getPrincipal();
        return jwtTokenUtil.sign(user.getUsername());
    }

    @Override
    public String loginByPhone(String phone, String authCode) {
        //验证authCode
        if (!"123".equals(authCode)) throw new SsoException(SSO_ERROR_AUTHCODE);
        Subject subject = SecurityUtils.getSubject();
        PhoneToken token = new PhoneToken(phone);
        subject.login(token);
        ShiroUser user = (ShiroUser) subject.getPrincipal();
        return jwtTokenUtil.sign(user.getUsername());
    }
}
