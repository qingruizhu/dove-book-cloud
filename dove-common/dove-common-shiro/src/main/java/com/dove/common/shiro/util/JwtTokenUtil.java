package com.dove.common.shiro.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dove.common.shiro.enm.ShiroErrorEnum;
import com.dove.common.shiro.exception.MyShiroException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * JwtToken生成的工具类
 * JWT token的格式：header.payload.signature
 * header的格式（算法、token的类型）：
 * {"alg": "HS512","typ": "JWT"}
 * payload的格式（用户名、创建时间、生成时间）：
 * {"sub":"wang","created":1489079981393,"exp":1489684781}
 * signature的生成算法：
 * HMACSHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
 */
public class JwtTokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

    private static final String CLAIM_KEY_UID = "uid";
    /**
     * 签名私钥(base64加密)
     */
    @Value("${jwt.secret:cWluZ3J1aXpodQ==}")//默认"qingruizhu"
    private String secret;
    @Value("${jwt.expiration:600}")//默认10分钟
    private Long expiration;

    /**
     * 生成签名
     *
     * @return token
     */
    public String sign(String uid) throws MyShiroException {
        if (StringUtils.isEmpty(uid))
            throw new MyShiroException(ShiroErrorEnum.JWT_ERROR_SIGN_HVT_KEY_UID);
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_UID, uid);
        return sign(claims);
    }

    /**
     * 生成签名
     *
     * @return token
     */
    public String sign(Map<String, Object> claims) throws MyShiroException {
        if (!claims.containsKey(CLAIM_KEY_UID))
            throw new MyShiroException(ShiroErrorEnum.JWT_ERROR_SIGN_HVT_KEY_UID);
        try {
            JWTCreator.Builder builder = JWT.create();
            claims.entrySet().stream().forEach((entry) -> {
                String name = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof String) {
                    builder.withClaim(name, (String) value);
                } else if (value instanceof Integer) {
                    builder.withClaim(name, (Integer) value);
                } else if (value instanceof Long) {
                    builder.withClaim(name, (Long) value);
                } else if (value instanceof Double) {
                    builder.withClaim(name, (Double) value);
                } else if (value instanceof Boolean) {
                    builder.withClaim(name, (Boolean) value);
                } else if (value instanceof Date) {
                    builder.withClaim(name, (Date) value);
                }
            });
            String uid = (String) claims.get(CLAIM_KEY_UID);
            return builder.withIssuedAt(new Date())//发布时间
                    .withExpiresAt(generateExpirationDate())//过期日
                    .sign(getAlgorithm(uid));
        } catch (MyShiroException e) {
            LOGGER.error(e.getMessage());
            throw new MyShiroException(ShiroErrorEnum.JWT_ERROR_SIGN);
        }
    }

    //生成签名的到期日
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);//毫秒值
    }

    //获取签名算法
    private Algorithm getAlgorithm(String uid) throws MyShiroException {
        StringBuffer realSecret = new StringBuffer(uid);
        realSecret.append(new String(Base64Utils.decodeFromString(secret)));
        try {
            return Algorithm.HMAC256(realSecret.toString());
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(ShiroErrorEnum.JWT_ERROR_GET_ALGORITHM.getMessage(), e);
            throw new MyShiroException(ShiroErrorEnum.JWT_ERROR_GET_ALGORITHM);
        }
    }

    /**
     * 验证token是否还有效
     *
     * @param token
     * @param uid
     */
    public boolean verify(String token, String uid) {
        try {
            JWTVerifier build = JWT.require(getAlgorithm(uid))
                    .withClaim(CLAIM_KEY_UID, uid).build();
            build.verify(token);
        } catch (MyShiroException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 获得token中 uid
     */
    public static String getUid(String token) {
        return getClaim(token, CLAIM_KEY_UID);
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     */
    public static String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            LOGGER.error("获取签名信息失败", e);
            return null;
        }
    }

    /**
     * 获取签发时间
     */
    public static Date getIssuedAt(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getIssuedAt();
        } catch (JWTDecodeException e) {
            LOGGER.error("获取签发时间失败", e);
            return null;
        }
    }

    /**
     * token是否过期
     *
     * @return true：过期
     */
    public static boolean isTokenExpired(String token) {
        return JWT.decode(token).getExpiresAt().before(new Date());
    }


}
