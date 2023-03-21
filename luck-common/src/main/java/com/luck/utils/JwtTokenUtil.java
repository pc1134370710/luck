package com.luck.utils;

import com.luck.constant.Constant;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.Map;

/**
 * @ClassName: JwtTokenUtil
 * TODO:类文件简单描述
 * @Version: 0.0.1
 */
@Slf4j
public class JwtTokenUtil {

    private static String securityKey="78944878877848fg)";
    private static Long tokenExpireTime=99999999l;
    private static String issuer="pc";

    public static void setJwtProperties(){

    }
    /**
     * 生成 access_token
     * @UpdateUser:
     * @Version:     0.0.1
     * @param subject
     * @param claims
     * @return       java.lang.String
     * @throws
     */
    public static String getAccessToken(String subject, Map<String,Object> claims){

        return generateToken(issuer,subject,claims,tokenExpireTime,securityKey);
    }


    /**
     * 签发token
     * @UpdateUser:
     * @Version:     0.0.1
     * @param issuer 签发人
     * @param subject 代表这个JWT的主体，即它的所有人 一般是用户id
     * @param claims 存储在JWT里面的信息 一般放些用户的权限/角色信息
     * @param ttlMillis 有效时间(毫秒)
     * @return       java.lang.String
     * @throws
     */
    public static String generateToken(String issuer, String subject, Map<String, Object> claims, long ttlMillis, String secret) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] signingKey = DatatypeConverter.parseBase64Binary(secret);

        JwtBuilder builder = Jwts.builder();
        builder.setHeaderParam("typ","JWT");
        if(null!=claims){
            builder.setClaims(claims);
        }
        if (!StringUtils.isEmpty(subject)) {
            builder.setSubject(subject);
        }
        if (!StringUtils.isEmpty(issuer)) {
            builder.setIssuer(issuer);
        }
        builder.setIssuedAt(now);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        builder.signWith(signatureAlgorithm, signingKey);
        return builder.compact();
    }

    /**
     * 从令牌中获取数据声明
     * @UpdateUser:
     * @Version:     0.0.1
     * @param token
     * @return       io.jsonwebtoken.Claims
     * @throws
     */
    public static Claims getClaimsFromToken(String token) {
        Claims claims=null;
        try {
            claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(securityKey)).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            if(e instanceof ClaimJwtException){
                claims=((ClaimJwtException) e).getClaims();
            }
        }
        return claims;
    }

    /**
     * 根据 key 获取 token 中的值
     * @param token
     * @param key
     * @return
     */
    public static  Object getKey(String token ,String key){
        Object username=null;
        try {
            Claims claims = getClaimsFromToken(token);
            username = (Object) claims .get(key);
        } catch (Exception e) {
            log.error("eror={}",e);
        }
        return username;
    }

    /**
     * 获取用户id
     * @UpdateUser:
     * @Version:     0.0.1
     * @param token
     * @return       java.lang.String
     * @throws
     */
    public static String getUserId(String token){
        String userId=null;
        try {
            Claims claims = getClaimsFromToken(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            log.error("eror={}",e);
        }
        return userId;
    }

    /**
     * 获取用户名
     * @UpdateUser:
     * @Version:     0.0.1
     * @param token
     * @return       java.lang.String
     * @throws
     */
    public static String getUserName(String token){

        String username=null;
        try {
            Claims claims = getClaimsFromToken(token);
            username = (String) claims .get(Constant.JWT_USER_NAME);
        } catch (Exception e) {
            log.error("eror={}",e);
        }
        return username;
    }

    /**
     * 验证token 是否过期(true:已过期 false:未过期)
     * @UpdateUser:
     * @Version:     0.0.1
     * @param token
     * @param secretKey
     * @return       java.lang.Boolean
     * @throws
     */
    public static Boolean isTokenExpired(String token) {

        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            log.error("error={}",e);
            return true;
        }
    }

    /**
     * 校验令牌(true：验证通过 false：验证失败)
     * @UpdateUser:
     * @Version:     0.0.1
     * @param token
     * @return       java.lang.Boolean
     * @throws
     */
    public static Boolean validateToken(String token) {
        Claims claimsFromToken = getClaimsFromToken(token);
        return (null!=claimsFromToken && !isTokenExpired(token));
    }

    /**
     * 获取token的剩余过期时间
     * @UpdateUser:
     * @Version:     0.0.1
     * @param token
     * @param secretKey
     * @return       long
     * @throws
     */
    public static long getRemainingTime(String token){
        long result=0;
        try {
            long nowMillis = System.currentTimeMillis();
            result= getClaimsFromToken(token).getExpiration().getTime()-nowMillis;
        } catch (Exception e) {
            log.error("error={}",e);
        }
        return result;
    }
}
