package com.dove.book.provider.sso.service.impl;

import com.dove.book.bgd.model.User;
import com.dove.book.bgd.service.IUserService;
import com.dove.book.provider.sso.enm.SsoErrorEnum;
import com.dove.book.provider.sso.exception.SsoException;
import com.dove.book.api.sso.dto.UserDto;
import com.dove.book.provider.sso.service.ISsoService;
import com.dove.book.provider.sso.util.RedisKeySso;
import com.dove.common.jwt.util.JwtTokenUtil;
import com.dove.common.redis.service.RedisService;
import com.dove.common.shiro.core.credential.ShiroUser;
import com.dove.common.shiro.core.util.RedisKeyShiro;
import com.dove.common.shiro.server.token.PhoneToken;
import com.dove.common.shiro.server.util.EncrypPwdUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;


/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/4/27 11:57
 */
@Service
public class SsoService implements ISsoService {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    RedisService redisService;
    @Autowired
    private IUserService<UserDto> userService;

    @Override
    public String login(String username, String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        subject.login(token);
        ShiroUser user = (ShiroUser) subject.getPrincipal();
        long issueTime = System.currentTimeMillis();
        String sign = jwtTokenUtil.sign(user.getUsername(), issueTime);
        cacheUserIssueTime(user, issueTime);
        return sign;
    }

    @Override
    public String loginByPhone(String phone, String authCode) {
        checkAuthCode(phone, authCode);
        Subject subject = SecurityUtils.getSubject();
        PhoneToken token = new PhoneToken(phone);
        subject.login(token);
        ShiroUser user = (ShiroUser) subject.getPrincipal();
        long issueTime = System.currentTimeMillis();
        String sign = jwtTokenUtil.sign(user.getUsername(), issueTime);
        cacheUserIssueTime(user, issueTime);
        return sign;
    }

    @Override
    public void regist(String username, String password) {
        User query = new User();
        query.setUsername(username);
        User user = userService.select(query);
        if (null != user) throw new SsoException(SsoErrorEnum.SSO_REGIST_ERROR_USER_EXIST);
        String salt = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
        query.setPassword(EncrypPwdUtil.sha256(password, salt));
        query.setPwdSalt(salt);
        if (userService.insert(query) <= 0) throw new SsoException(SsoErrorEnum.SSO_REGIST_ERROR);
    }

    @Override
    public void registByPhone(String phone, String authCode) {
        User query = new User();
        query.setPhone(phone);
        User user = userService.select(query);
        if (null != user) throw new SsoException(SsoErrorEnum.SSO_REGIST_ERROR_PHONE_EXIST);
        checkAuthCode(phone, authCode);
        if (userService.insert(query) <= 0) throw new SsoException(SsoErrorEnum.SSO_REGIST_ERROR);
    }

    @Override
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) subject.getPrincipal();
        subject.logout();
        redisService.del(RedisKeyShiro.tokenIssueTime(user.getUsername()));
        redisService.del(RedisKeyShiro.user(user.getUsername()));
    }

    /**
     * 验证authCode
     */
    private void checkAuthCode(String phone, String authCode) {
        if (redisService.exist(RedisKeySso.authCode(phone))) {
            String cacheCode = redisService.get(RedisKeySso.authCode(phone));
            if (!cacheCode.equalsIgnoreCase(authCode)) {
                throw new SsoException(SsoErrorEnum.SSO_COMMON_ERROR_AUTHCODE_WRONG);
            }
        }
        throw new SsoException(SsoErrorEnum.SSO_COMMON_ERROR_AUTHCODE_INVALID);
    }

    /**
     * 缓存 -> token发布时间 + 用户
     */
    private void cacheUserIssueTime(ShiroUser user, long issueTime) {
        redisService.set(RedisKeyShiro.tokenIssueTime(user.getUsername()), issueTime, jwtTokenUtil.getExpiration());
        redisService.set(RedisKeyShiro.user(user.getUsername()), user, jwtTokenUtil.getExpiration());
    }
}
