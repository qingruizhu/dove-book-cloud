package com.dove.book.provider.sso.util;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/4/30 09:34
 */
public class RedisKeySso {
    /**
     * The 验证码 PREFIX_AUTHCODE.
     */
    private static final String SSO_PREFIX_AUTHCODE = "sso:authCode:";
    private static String COLON = ":";

    public static String authCode(String subject) {
        StringBuffer sb = new StringBuffer(SSO_PREFIX_AUTHCODE);
        sb.append(COLON);
        sb.append(subject);
        return sb.toString();
    }

}
