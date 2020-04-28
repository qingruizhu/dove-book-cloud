package com.dove.common.jwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dove.common.jwt.enm.JwtErrorEnum;
import com.dove.common.jwt.exception.DoveJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final String CLAIM_KEY_SUB = "subject";
    /**
     * 签名私钥(base64加密)
     */
//    @Value("${jwt.secret:cWluZ3J1aXpodQ==}")//默认"qingruizhu"
    private String secret;
    //    @Value("${jwt.expiration:600}")//默认10分钟
    private Long expiration = (long) 600;

    /**
     * @param secret -> Base64Utils.decodeFromString(secret) base64解密后再传入
     */
    public JwtTokenUtil(String secret) {
        this.secret = secret;
    }

    public JwtTokenUtil(String secret, Long expiration) {
        this.secret = secret;
        this.expiration = expiration;
    }

    /**
     * 生成签名
     *
     * @return token
     */
    public String sign(String subject) throws DoveJwtException {
        if (null == subject || "".equals(subject))
            throw new DoveJwtException(JwtErrorEnum.JWT_ERROR_SIGN_HVT_KEY_UID);
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_SUB, subject);
        return sign(claims);
    }

    /**
     * 生成签名
     *
     * @return token
     */
    public String sign(Map<String, Object> claims) throws DoveJwtException {
        if (!claims.containsKey(CLAIM_KEY_SUB))
            throw new DoveJwtException(JwtErrorEnum.JWT_ERROR_SIGN_HVT_KEY_UID);
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
            String subject = (String) claims.get(CLAIM_KEY_SUB);
            return builder.withIssuedAt(new Date())//发布时间
                    .withExpiresAt(generateExpirationDate())//过期日
                    .sign(getAlgorithm(subject));
        } catch (IllegalArgumentException | JWTCreationException e) {
            LOGGER.error("生成token失败:{}", e.getMessage());
            throw new DoveJwtException(JwtErrorEnum.JWT_ERROR_SIGN);
        }
    }

    //生成签名的到期日
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);//毫秒值
    }

    //获取签名算法
    private Algorithm getAlgorithm(String subject) throws DoveJwtException {
        StringBuffer realSecret = new StringBuffer(subject);
        realSecret.append(secret);
        try {
            return Algorithm.HMAC256(realSecret.toString());
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(JwtErrorEnum.JWT_ERROR_GET_ALGORITHM.getMessage(), e);
            throw new DoveJwtException(JwtErrorEnum.JWT_ERROR_GET_ALGORITHM);
        }
    }

    /**
     * 验证token是否还有效
     *
     * @param token
     * @param subject
     */
    public boolean verify(String token, String subject) {
        try {
            JWTVerifier build = JWT.require(getAlgorithm(subject))
                    .withClaim(CLAIM_KEY_SUB, subject).build();
            build.verify(token);
        } catch (JWTVerificationException e) {
            LOGGER.error("token非法:{}", e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 获得token中 subject
     */
    public static String getSubject(String token) {
        return getClaim(token, CLAIM_KEY_SUB);
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     */
    public static String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            LOGGER.error("获取签名信息失败:{}", e.getMessage());
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
            LOGGER.error("获取签发时间失败:{}", e.getMessage());
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
