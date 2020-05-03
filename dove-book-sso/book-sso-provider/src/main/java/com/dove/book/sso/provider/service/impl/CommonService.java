package com.dove.book.sso.provider.service.impl;

import com.dove.book.sso.provider.exception.SsoException;
import com.dove.book.sso.provider.service.ICommonService;
import com.dove.book.sso.provider.util.RedisKeySso;
import com.dove.common.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.dove.book.sso.provider.enm.SsoErrorEnum.SSO_COMMON_ERROR_AUTHCODE_INVALID;
import static com.dove.book.sso.provider.enm.SsoErrorEnum.SSO_COMMON_ERROR_AUTHCODE_WRONG;


/**
 * @Description: 公共业务
 * @Auther: qingruizhu
 * @Date: 2020/4/30 09:28
 */
@Service
public class CommonService implements ICommonService {

    @Autowired
    private RedisService redisService;
    @Value("${sso.authcode.expiration:60}")
    private long authCodeExpire;

    @Override
    public boolean cacheAuthCode(String randomPrefix, String randomCode) {
        //TODO 发送短信
        return redisService.set(RedisKeySso.authCode(randomPrefix), randomCode, authCodeExpire);
    }

    @Override
    public void checkAuthCode(String authPrefix, String authCode) {
        if (redisService.exist(RedisKeySso.authCode(authPrefix))) {
            String cacheCode = redisService.get(RedisKeySso.authCode(authPrefix));
            if (!cacheCode.equalsIgnoreCase(authCode)) {
                throw new SsoException(SSO_COMMON_ERROR_AUTHCODE_WRONG);
            }
        }
        throw new SsoException(SSO_COMMON_ERROR_AUTHCODE_INVALID);
    }
}
