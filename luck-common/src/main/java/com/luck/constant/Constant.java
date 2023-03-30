package com.luck.constant;

/**
 * @description: 常量
 * @author: pangcheng
 * @create: 2023-02-20 22:41
 **/
public class Constant {

    public static final String JWT_USER_TOKEN="token";

    public static final String JWT_USER_NAME = "jwt-userName";

    /**
     * rsa 公私钥前缀
     */
    public static final String RSA_PRIVATE = "private";
    public static final String RSA_PUBLIC = "public";



    /**
     *  redis  用户认证信息 key
     */
    public static final String REDIS_USER_KEY = "authToken";
    /**
     *  会话过期时间 24小时，单位毫秒,
     */
    public static final long REDIS_USER_AUTO_TIME_EXP = 1000 * 60 * 60* 24;


    public static final String ACCESS_TOKEN = "authToken";

    /**
     * 普通用户类型
     */
    public static final int COMMON_USER= 0;

    /**
     * 管理员
     */
    public static final int ADMIN_USER= 10;
}
