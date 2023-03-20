package com.hyt.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 前端与后端实现RSA加密类
 * 对RSAUtils进行封闭，方便调用
 */
@Component
@Slf4j
public class PasswordRSAUtil {

    @Autowired
    private RedisUtils redisUtils;

    public String getPublicKey(String key){
        try {
            Map<String, Object> map = RSAUtils.genKeyPair();
            // 获取私钥
            String privateKey = RSAUtils.getPrivateKey(map);
            // 获取公钥
            String publicKey = RSAUtils.getPublicKey(map);
            redisUtils.set(key+"private", privateKey,3L, TimeUnit.MINUTES);
            redisUtils.set(key+"public",publicKey,3L, TimeUnit.MINUTES);
            return publicKey;
        } catch (Exception e) {
            log.error("cmd=getPublicKey |msg = 获取公钥失败",e);
        }
        return null;
//
    }

    public String decrypt(String key,String password) {
        String privateKey = (String)redisUtils.get(key+"private");

        if(privateKey==null) {
            return null;
        }
        try {

            return RSAUtils.decryptDataOnJava(password, privateKey);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("cmd=PasswordRSAUtil | msg =解密失败",e);
        }
        return null;
    }

//    public static void main(String[] args) throws Exception {
//
////        RSAUtils.
//    }


}
